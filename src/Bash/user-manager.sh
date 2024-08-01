#!/bin/bash

USER_STORE="/home/tresor/Desktop/CMU-Gahinga-2-LPMT-challenge/src/Storage/user-store.txt"
PATIENTS_STORE="/home/tresor/Desktop/CMU-Gahinga-2-LPMT-challenge/src/Storage/patients-store.txt"
INITIAL_ADMIN_EMAIL="admin@example.com"
INITIAL_ADMIN_PASSWORD="admin123"

# Function to initialize user-store.txt if it doesn't exist and no admin exists
initialize_user_store() {
  if [ ! -f $USER_STORE ]; then
    touch $USER_STORE
    touch $PATIENTS_STORE
    initial_password_hash=$(echo -n "$INITIAL_ADMIN_PASSWORD" | sha256sum | awk '{print $1}')
    echo "$INITIAL_ADMIN_EMAIL,,$initial_password_hash,ADMIN,true" >> $USER_STORE
    echo "Initialized user store with the initial admin user."
  else
    # Check if there is already an admin user
    if grep -q ",,,$INITIAL_ADMIN_PASSWORD,ADMIN," "$USER_STORE"; then
      echo "Admin already exists. Initialization skipped."
      exit 0
    else
      initial_password_hash=$(echo -n "$INITIAL_ADMIN_PASSWORD" | sha256sum | awk '{print $1}')
      echo "$INITIAL_ADMIN_EMAIL,,$initial_password_hash,ADMIN,true" >> $USER_STORE
      echo "Initialized user store with the initial admin user."
    fi
  fi
}

# Function to initiate user registration
initiate_registration() {

	if [ $1 != "ADMIN" ]; then
		echo "You have no access to initiate registration"
		exit 0
	fi
	email=$2

  if ! [[ "$email" =~ ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$ ]]; then
    echo "Invalid email format."
    exit 0
  fi

  uuid=$(uuidgen)
  echo "$email,$uuid,,PATIENT,false" >> $USER_STORE
  echo "Registration initiated. Use the following UUID to complete registration: $uuid"
}

complete_registration() {
 
  if [ $# -ne 10 ]; then
    echo "Usage: $0 complete-registration <uuid> <firstName> <lastName> <dateOfBirth> <hasHIV> <diagnosisDate> <onART> <artStartDate> <countryISO> <password>"
    exit 0
  fi

  uuid=$1
  firstName=$2
  lastName=$3
  dateOfBirth=$4
  hasHIV=$5
  diagnosisDate=$6
  onART=$7
  artStartDate=$8
  countryISO=$9
  password=${10}

  password_hash=$(echo -n "$password" | sha256sum | awk '{print $1}')

  # Check if the UUID is already used
  if ! grep -q ",$uuid,," "$USER_STORE"; then
    echo "User was not initiated"
    exit 0
  fi

  # Create a temporary file
  temp_file=$(mktemp)

  # Update the user record and add patient details
  awk -v uuid="$uuid" -v password_hash="$password_hash" \
    'BEGIN { FS=","; OFS="," }
     {
       if ($2 == uuid) {
         $3 = password_hash
         $5 = "true"
       }
       print
     }' "$USER_STORE" > "$temp_file" && mv "$temp_file" "$USER_STORE"

  echo "$uuid,$firstName,$lastName,$dateOfBirth,$hasHIV,$diagnosisDate,$onART,$artStartDate,$countryISO,," >> "$PATIENTS_STORE"
  echo "Registration completed for user with UUID: $uuid"
}

login() {
  email=$1
  password=$2
  password_hash=$(echo -n "$password" | sha256sum | awk '{print $1}')

  while IFS=, read -r stored_email stored_uuid stored_password_hash access_type is_registered
  do
    if [[ "$stored_email" == "$email" ]]; then
      if [[ "$stored_password_hash" == "$password_hash" ]]; then
        if [[ "$is_registered" == "true" ]]; then
	 echo "$access_type,$stored_uuid"
	else
	 echo "User should first complete the registration"
	fi
	return 0
      else
        echo "Login failed. Incorrect password."
        return 0
      fi
    fi
  done < "$USER_STORE"

  echo "Login failed. Email not found."
  return 0
}

view_profile() {
  uuid_code=$1

  while IFS=, read -r stored_uuid firstname lastname dateOfBirth isHivPositive dateOfInfection onArtDrugs startARTDate country demiseDate lifeExpectancy
  do
    if [[ "$stored_uuid" == "$uuid_code" ]]; then
      echo "First Name: $firstname"
      echo "Last Name: $lastname"
      echo "Date of Birth: $dateOfBirth"
      echo "Is HIV Positive: $isHivPositive"
      echo "Date of Infection: $dateOfInfection"
      echo "On ART Drugs: $onArtDrugs"
      echo "Date of start ART: $startARTDate"
      echo "Country: $country"
      echo "Demise Date: $demiseDate"
      echo "Life Expectancy: $lifeExpectancy"
      return 0
    fi
  done < "$PATIENTS_STORE"

  echo "Profile not found for the given UUID."
  return 0
}


# CLI Arguments Handling
case $1 in
  "initialize-user-store")
    initialize_user_store
    ;;
  "initiate-registration")
    if [ $# -ne 3 ]; then
      echo "Usage: $0 initiate-registration <UserRole> <email>"
      exit 1
    fi
    initiate_registration $2 $3
    ;;
  "complete-registration")
    shift
    complete_registration "$@"
    ;;
  "view-profile")
    if [ $# -ne 2 ]; then
      echo "Usage: $0 view-profile <UUID_code>"
      exit 1
    fi
    view_profile $2
    ;;
  "login")
    if [ $# -ne 3 ]; then
      echo "Usage: $0 login <email> <password>"
      exit 1
    fi
    login $2 $3
    ;;
  *)
    echo "Unknown command: $1"
    echo "Usage: $0 <initialize-user-store|initiate-registration|complete-registration|view-profile|login> [<args>]"
    exit 1
    ;;
esac
