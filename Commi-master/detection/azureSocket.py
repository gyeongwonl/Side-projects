# app.py
import os, base64, json, requests
from flask import Flask, render_template, request
import time
import CommandClass

# Load system variables with dotenv
from dotenv import load_dotenv
load_dotenv()

# Load keys

from azure.cognitiveservices.vision.customvision.training import CustomVisionTrainingClient
from azure.cognitiveservices.vision.customvision.training.models import ImageFileCreateEntry
from azure.cognitiveservices.vision.customvision.prediction import CustomVisionPredictionClient

# endpoint = os.environ["ENDPOINT"]
training_key = os.environ["TRAINING_KEY"]
prediction_key = os.environ["PREDICTION_KEY"]
prediction_resource_id = os.environ["PREDICTION_RESOURCE_ID"]

publish_iteration_name = "iter3"

trainer = CustomVisionTrainingClient(training_key, endpoint)

# Create a new project
projectID = "9a8d2d16-8893-4e96-8135-cae44d68358a"

#Can be used to train instead of using the customvision webpage 
def train():
    print ("Training...")
    iteration = trainer.train_project(projectID)
    while (iteration.status != "Completed"):
        iteration = trainer.get_iteration(projectID, iteration.id)
        print ("Training status: " + iteration.status)
        time.sleep(1)

    #The iteration is now trained. Publish it to the project endpoint
    trainer.publish_iteration(projectID, iteration.id, publish_iteration_name, prediction_resource_id)
    print ("Done!")

def parse(list):
    ddlis = []
    listLen = len(list);
    for i in range(0, listLen):
   
        ddlis.append(list[i].split(":"))

    return ddlis

def getCommand(dLis):
    listLen = len(dLis)
    for i in range(0, listLen):
        if(float(dLis[i][1][0:2]) > 75):
            return dLis[i][0]
        else:
            return "Cannot recognize sign"

def getListOfPredictions(frameDir):
# Now there is a trained endpoint that can be used to make a prediction
    predictor = CustomVisionPredictionClient(prediction_key, endpoint)
    l = []
    # "images/frame73.jpg"
    with open(frameDir, "rb") as image_contents:
        results = predictor.classify_image(
            projectID, publish_iteration_name, image_contents.read())

        # Display the results.
        for prediction in results.predictions:
            tag = "\t" + prediction.tag_name + ":{0:.2f}%".format(prediction.probability * 100)
            l.append(tag)
            print(tag)

    li = parse(l)
    return li

def getMostLikelyCommand(predictionsList):

    listLen = len(predictionsList)
    bestTag = predictionsList[0][0]
    maxScore = float(str(predictionsList[0][1])[:-1])

    for i in range(1, listLen):
        currScore = float(str(predictionsList[i][1])[:-1])
        if (currScore > maxScore):
            maxScore = currScore
            bestTag = predictionsList[i][0]
    
    command = CommandClass.commandClass(bestTag, maxScore)
    return command

def signCommandDeterminator():       
    directory = os.fsencode("images/")

    wholeCommandSentence = ""
    prevCommand = ""
    currCommand = ""
    for file in os.listdir(directory):
        fileName = os.fsencode(file)
        framePath = "images/" + str(fileName)[2:-1]
        predictionsList = getListOfPredictions(framePath)
        listLen = len(predictionsList)
        prevCommand = getMostLikelyCommand(predictionsList)

    for file in os.listdir(directory):
        fileName = os.fsencode(file)
        framePath = "images/" + str(fileName)[2:-1]
        predictionsList = getListOfPredictions(framePath)
        listLen = len(predictionsList)

        currCommand = getMostLikelyCommand(predictionsList)
        if (prevCommand.tag != currCommand.tag):
            wholeCommandSentence += prevCommand.tag
        else:
            prevCommand = currCommand

    if (wholeCommandSentence == ""):
        wholeCommandSentence += prevCommand.tag

    print(wholeCommandSentence)
    return wholeCommandSentence
