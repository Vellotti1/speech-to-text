const recordButton = document.getElementById("recordButton");
const status = document.getElementById("status");
const transcriptionText = document.getElementById("transcriptionText");
const reocrdingHistory = document.getElementById("recordingHistory");

let mediaRecorder;
let audioChunks =[];

recordButton.addEventListener("click", async () => {
    if (!mediaRecorder || mediaRecorder.state === "inactive") {
        await startRecording();
    } else {
        stopRecording();
    }
});

async function startRecording() {
    try {
        const stream = await navigator.mediaDevices.getUserMedia({audio:true});
        

        audioChunks = [];
        mediaRecorder = new MediaRecorder(stream);

        mediaRecorder.addEventListener("dataavailable", event => {
            audioChunks.push(event.data);
        });

        mediaRecorder.addEventListener("stop", async () => {
            stream.getTracks().forEach(track => track.stop());
        

        const audioBlob = new Blob(audioChunks, {
            type: mediaRecorder.mimeType
        });

        await uploadAudio(audioBlob);
        });

        async function uploadAudio(audioBlob) {
            const formData = new FormData();

            formData.append("audio", audioBlob, "recording.webm");

            try {
                const response = await fetch("/api/audio", {
                    method: "POST",
                    body: formData
                });

                if (!response.ok) {
                    throw new Error("Upload failure")
                }

                const message = await response.text();
                transcriptionText.textContent = message;
                addToHistory(nessage);
                status.textContent = "Transcription success!";
            } catch(error) {
            console.error(error);
            status.textContent = "Failed to upload recording"
        } 
        }

        mediaRecorder.start();

        recordButton.textContent = "End Recording";
        status.textContent = "Now Recording...";   
    } catch (error) {
        status.textContent = "Please enable Microphone.";
        console.error(error);
    }

}

function stopRecording() {
    mediaRecorder.stop();

    recordButton.textContent = "Start Recording";
    status.textContent = "Recording Processing";
}

function addToHistory(transcription) {
    const historyItem = document.createElement("p")

    historyItem.textContent = transcription;

    reocrdingHistory.appendChild(historyItem);
}