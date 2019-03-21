node {
    properties([parameters([string(defaultValue: '127.0.0.1', description: 'Please give IP to build a site', name: 'IP', trim: true)])])
    stage("Install git"){
        sh "ssh    ec2-user@${IP}         sudo yum install git python-pip  -y"
    }
    stage("Clone a repo"){
        git 'https://github.com/farrukh90/Flaskex.git'
    }
    stage("Run App"){
        try{
            sh "ssh    ec2-user@${IP}       sudo mkdir  /flaskex 2> /dev/null"
        }
        catch(exc){
            sh "echo folder exists"
        }
    }
    stage("Copy files"){
        sh "scp -r *   ec2-user@${IP}:/home/ec2-user/"
    }
    stage("Move files to /flaskex"){
        try{
            sh "ssh    ec2-user@${IP}      sudo mv  /home/ec2-user/*  /flaskex/"
        }
        catch(exc){
            sh "echo Folders moved"
        }
    }
    stage("Install requirements"){
        sh "ssh    ec2-user@${IP}      sudo pip install -r /flaskex/requirements.txt"
    }
    stage("move service to /etc"){
        sh "ssh    ec2-user@${IP}      sudo mv /flaskex/flaskex.service /etc/systemd/system"
    }
    stage("Start service"){
        sh "ssh    ec2-user@${IP}       sudo systemctl start flaskex"
    }
}