const recordBytton = document.getElementById("recordButton");
const status = document.getElementById("status");

recordButton.addEventListener("click", () => {
    status.textContent = "Recording In Progress..."
});