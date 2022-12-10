import os
import subprocess


os.system('sudo docker rm -f $(sudo docker ps | grep "hypercare" | cut -d " " -f 1)')

os.system('sudo docker image rm hypercare')






# os.popen('sudo docker rm -f $(sudo docker ps | grep "hypercare" | cut -d " " -f 1)').read()


