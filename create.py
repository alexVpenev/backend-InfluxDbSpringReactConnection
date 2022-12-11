import os
import subprocess




os.system('gradle build')

os.system('sudo docker build --build-arg JAR_FILE=build/libs/\*.jar -t hypercare .')

os.system('sudo docker run -p 2525:8081 hypercare')






# os.popen('sudo docker rm -f $(sudo docker ps | grep "hypercare" | cut -d " " -f 1)').read()


