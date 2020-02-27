
#maven arg for select profile : -Dspring-boot.run.profiles={profile name}
#mvn clean compile spring-boot:run -Dspring-boot.run.profiles=dev

# mvn spring-boot:run -Dspring-boot.run.arguments="--initialize=all","--JobName=all"
mvn spring-boot:run -Dspring-boot.run.arguments="--JobName=all"
"args": ["--spring.profiles.active=dev", "--initialize=all","--JobName=job_list"]


<---//////// IntelliJ Add Args ////////--->
--spring.profiles.active=dev --JobName=all --initialize=all

--spring.profiles.active=dev --initialize=all --JobName=all


mvn spring-boot:run -Dspring-boot.run.arguments="--initialize=all"

mvn spring-boot:run -Dspring-boot.run.arguments="--JobName=all"

--spring.profiles.active=dev --JobName=datapump --field_name=Empoyee_No/Empoyee_Name/FirstName/LastName/JobGrade/Contract_Type/Birthday/Status/Sex/Age/Update_DT/Create_DT --row_num=1000 --file_name=Employee1
# mvn spring-boot:run -Dspring-boot.run.arguments="--JobName=datapump","--field_name=Empoyee_No/Empoyee_Name/FirstName/LastName/JobGrade/Contract_Type/Birthday/Status/Sex/Age/Update_DT/Create_DT","--row_num=1000","--file_name=Test6"

