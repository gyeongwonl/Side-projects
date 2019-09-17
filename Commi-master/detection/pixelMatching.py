from skimage.measure import compare_ssim
import argparse
import imutils
import cv2

def matchPixels(firstFrame, secondFrame):
    grayA = cv2.cvtColor(firstFrame, cv2.COLOR_BGR2GRAY)
    grayB = cv2.cvtColor(secondFrame, cv2.COLOR_BGR2GRAY)

    (score, diff) = compare_ssim(grayA, grayB, full=True)
    diff = (diff * 255).astype("uint8")
    return score * 100