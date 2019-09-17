import cv2
import numpy as np
import keyboard
import pixelMatching
import shutil
import os

cap = cv2.VideoCapture(0)

fourcc = cv2.VideoWriter_fourcc(*'DIVX')
currentFrame = 0
collectFrames = False

if (os.path.isdir('./images')):
    shutil.rmtree('./images')  
    os.mkdir('./images') 
else:
    os.mkdir('./images')

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

        if (collectFrames & (currentFrame % 3 == 0)):
            print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            name = './images/frame' + str(currentFrame) + '.jpg'
            cv2.imwrite(name, frame)
            
            currentFrame += 1
            cv2.imshow("frame", frame)
            print("Helll00000000000000000000000000000000000000000000000")
            if (keyboard.is_pressed('h')):
                print('ypu you done goofed up')
                break
        else:
            currentFrame += 1

        if (keyboard.is_pressed('h')):
            print('ypu you done goofed up')
            break
    else:
        break
    
cap.release()
cv2.destroyAllWindows()
