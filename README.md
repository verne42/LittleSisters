# Verne 42 LittleSisters
Repository for Little Sisters Projects

# Who are we?

Verne42 is an OpenSource community created by mathematicians, web developers, engineers, philosophers, painters, designers, lawyers, illustrators, chefs, architects, entrepreneurs, biochemists, and artists. Our common ground as citizens is to improve our society, and we welcome everyone that wants to be part of our community.

“Little Sisters” is our first OpenSource project, which aims at simplifying the legislation flow in a clear, concise, and more comprehensible way in order to be able to demand accountability from our legislators.


“Oley” is our first Little Sister. As a first iteration, the functionality is still limited, but the data is real. We need your feedback to get this project running! 


Please let us know your thoughts by contacting us at feedback.oley@verne42.com

We will be creating issues with the feedback we receive.

You can test our deployed beta version:
http://verne-42.com

# Development

Little Sister Oley is a maven project.
It uses neo4j as graph database and it integrate it with SpringBoot as backend.
Thymeleaf + bootstrap are used as frontend.

Since it is a maven project you can import it to your IDE.

It creates a docker image you can run:
mvn clean install -Pdocker

docker run -e "NEO4J_AUTH=neo4j/testing" --publish=7474:7474 --publish=7687:7687 --volume=/little-sisters-verne42/neo4j/data:/data --volume=/little-sisters-verne42/neo4j/logs:/logs -d --restart=always --name verne42-oley-neo4j neo4j


docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=oley.import.csv" -e "Neo4j_Password=testing" -e "Neo4j_url=bolt://verne42-oley-neo4j" -e "OLEY_CSV_ROOT=file:/opt/Verne42/oley/data/" --name verne42-oley --link verne42-oley-neo4j:neo4j docker.repository.littlesisters.verne42.com/little-sisters-oley:0.0.1-beta


Note that on the first run you should import the data. By default we include the data from the first period of the Mexican legislation flow.

You can override this data with your data. You will need to create a volume to the container with your data in the following structure:

data/$(Country)
	/associations.csv (name,president,secretary,totalMembers,foundationDate,ideologiesList,socialNetworksList,politicalPosturesList,address,image)
	/$(Legislature)
		/bills.csv (billId,pdfURL,topics,voted,text,status,IN_FAVOR,AGAINST,ABSTENTION,ABSENCES)
		/legislators.csv (legislatorId,name,electedBy,commissionsList,representedEntity,association,substitute,born,email,phone,photo,congressAbsences,ideologies,facebook,twitter,instagram,linkedin)

Notes: 
- Empty values should be replace with null string.
- Properties that are lists should be separated by the pipe char '|'
- You can add as many countries and legislatures as you want.

