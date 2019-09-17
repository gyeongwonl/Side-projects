from ReadGoogleSheets import request
from subprocess import Popen, PIPE

GOOGLE_ASSISTANT_TRIGGER_COMMAND = ["python", "-m", "googlesamples.assistant.grpc.textinput", "--device-id", "'0'", "--device-model-id", "'1'"]

def send_text_request_to_google_assistant(request):

	p = Popen(GOOGLE_ASSISTANT_TRIGGER_COMMAND, shell=True, stdin=PIPE, stdout=PIPE, stderr=PIPE)
	p.stdin.write(request.encode('utf-8'))
	return p.communicate()[0].decode('utf-8')

def save_dialogue_to_file(response):

	f = open("dialogue.txt", "w")
	f.write(response)
	f.close()

A = send_text_request_to_google_assistant(request)
print(A)
save_dialogue_to_file(A)
