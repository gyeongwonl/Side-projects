import json
import gspread
from oauth2client.client import SignedJwtAssertionCredentials

json_key = json.load(open('creds.json')) # json credentials you downloaded earlier
scope = ['https://spreadsheets.google.com/feeds',
        'https://www.googleapis.com/auth/drive']

credentials = SignedJwtAssertionCredentials(json_key['client_email'], json_key['private_key'].encode(), scope) # get email and key from creds

file = gspread.authorize(credentials) # authenticate with Google
sheet = file.open("Python Read/Write").sheet1 # open sheet

request = sheet.acell('A1').value
print(request)