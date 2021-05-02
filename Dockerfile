FROM python:latest
RUN apt update 
RUN apt install git 
RUN apt install python-pip -y
RUN git clone https://github.com/farrukh90/Flaskex.git && cd Flaskex 
RUN cd Flaskex && pip install -r requirements.txt
CMD cd Flaskex && python app.py
EXPOSE 4000
# docker build -t  farrukhsadykov/flaskex:3  .
# docker run farrukhsadykov/flaskex:3 -p 4000:4000
