const recordButton = document.getElementById("recordButton");
const status = document.getElementById("status");

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

        mediaRecorder.addEventListener("stop", () => {
            stream.getTracks().forEach(track => track.stop());
        });

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
    status.textContent = "Recording Ended";
}