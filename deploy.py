import os
import subprocess


os.system('git pull')

os.system('gradle build')

os.system('sudo docker build --build-arg JAR_FILE=build/libs/\*.jar -t hypercare .')

os.system('sudo docker run -p 2525:8081 hypercare')

os.system('sudo docker rm -f $(sudo docker ps | grep "hypercare" | cut -d " " -f 1)')

os.system('sudo docker image rm hypercare')






# os.popen('sudo docker rm -f $(sudo docker ps | grep "hypercare" | cut -d " " -f 1)').read()


