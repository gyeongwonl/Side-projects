# app.py
import os, base64, json, requests
from flask import Flask, render_template, request
import time
import CommandClass
import cv2
import numpy as np
import keyboard
import pixelMatching
import shutil
import azureSocket

# # Load system variables with dotenv
from dotenv import load_dotenv
load_dotenv()

# # Load keys

# from azure.cognitiveservices.vision.customvision.training import CustomVisionTrainingClient
# from azure.cognitiveservices.vision.customvision.training.models import ImageFileCreateEntry
# from azure.cognitiveservices.vision.customvision.prediction import CustomVisionPredictionClient

# # endpoint = os.environ["ENDPOINT"]
# training_key = os.environ["TRAINING_KEY"]
# prediction_key = os.environ["PREDICTION_KEY"]
# prediction_resource_id = os.environ["PREDICTION_RESOURCE_ID"]

# publish_iteration_name = "iter3"

# trainer = CustomVisionTrainingClient(training_key, endpoint)

# # Create a new project
# projectID = "9a8d2d16-8893-4e96-8135-cae44d68358a"

# #Can be used to train instead of using the customvision webpage 
# def train():
#     print ("Training...")
#     iteration = trainer.train_project(projectID)
#     while (iteration.status != "Completed"):
#         iteration = trainer.get_iteration(projectID, iteration.id)
#         print ("Training status: " + iteration.status)
#         time.sleep(1)

#     #The iteration is now trained. Publish it to the project endpoint
#     trainer.publish_iteration(projectID, iteration.id, publish_iteration_name, prediction_resource_id)
#     print ("Done!")

# def parse(list):
#     ddlis = []
#     listLen = len(list);
#     for i in range(0, listLen):
   
#         ddlis.append(list[i].split(":"))

#     return ddlis

# def getstartStopCommand():
# # Now there is a trained endpoint that can be used to make a prediction
#     predictor = CustomVisionPredictionClient(prediction_key, endpoint)
#     l = []
#     # "images/frame73.jpg"
#     with open("startStop.jpg", "rb") as image_contents:
#         results = predictor.classify_image(
#             projectID, publish_iteration_name, image_contents.read())

#         # Display the results.
#         for prediction in results.predictions:
#             tag = prediction.tag_name + " :{0:.2f}%".format(prediction.probability * 100)
#             l.append(tag)

#     li = parse(l)
#     startStopCommand = CommandClass.commandClass(li[0][0], li[0][1])
#     return startStopCommand

def emptyDirectory():
    if (os.path.isdir('./images')):
        shutil.rmtree('./images')  
        os.mkdir('./images') 
    else:
        os.mkdir('./images')

def cvDetection():
    cap = cv2.VideoCapture(0)
    fourcc = cv2.VideoWriter_fourcc(*'DIVX')
    currentFrameNum = 0
    collectFrames = False
    for retry in range(100):
        try:
            emptyDirectory()
            break
        except:
            print("Unable to empty directory. Just try again")


    while(cap.isOpened()):
        ret, frame = cap.read()
        if (ret == True):
            newframe = cv2.flip(frame, 0)
            ret, newframe = cap.read()
            score = pixelMatching.matchPixels(frame, newframe)
            if (score >= 96):
                collectFrames = False
            if (score <= 96):
                collectFrames = True
            startStopCollection(collectFrames, currentFrameNum, frame)
        else:
            break  
    cap.release()
    cv2.destroyAllWindows()

def startStopCollection(collectFrames, currentFrameNum, frame):
   
    if (collectFrames & (currentFrameNum % 3 == 0)):
        print("----------------------------The Feed Is Running----------------------------")
        name = './images/frame' + str(currentFrameNum) + '.jpg'
        cv2.imwrite(name, frame)
        currentFrameNum += 1
        cv2.imshow("frame", frame)
        print("----------------------------The Feed Is Running----------------------------")
    else:
        currentFrameNum += 1

def runDetection():
    cvDetection()
    #azureSocket.signCommandDeterminator()

runDetection()