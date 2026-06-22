<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Advanced To-Do App</title>

<style>
*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:'Segoe UI',sans-serif;
}

body{
    min-height:100vh;
    display:flex;
    justify-content:center;
    align-items:center;
    padding:20px;
    background:linear-gradient(135deg,#3b82f6,#8b5cf6);
}

.container{
    width:100%;
    max-width:600px;
    background:rgba(255,255,255,0.15);
    backdrop-filter:blur(15px);
    border:1px solid rgba(255,255,255,.2);
    border-radius:20px;
    padding:25px;
    color:white;
    box-shadow:0 10px 30px rgba(0,0,0,.2);
}

h1{
    text-align:center;
    margin-bottom:20px;
}

.input-group{
    display:flex;
    gap:10px;
    margin-bottom:15px;
}

input{
    flex:1;
    padding:12px;
    border:none;
    border-radius:10px;
    outline:none;
}

button{
    border:none;
    cursor:pointer;
    border-radius:10px;
    transition:.3s;
}

.add-btn{
    padding:12px 20px;
    background:#10b981;
    color:white;
}

.add-btn:hover{
    transform:translateY(-2px);
}

.filters{
    display:flex;
    gap:10px;
    flex-wrap:wrap;
    margin:15px 0;
}

.filters button{
    padding:8px 15px;
    background:rgba(255,255,255,.2);
    color:white;
}

.filters button:hover{
    background:rgba(255,255,255,.3);
}

.stats{
    display:flex;
    justify-content:space-between;
    margin-bottom:15px;
}

ul{
    list-style:none;
}

li{
    display:flex;
    justify-content:space-between;
    align-items:center;
    background:rgba(255,255,255,.15);
    margin-bottom:10px;
    padding:12px;
    border-radius:12px;
}

.task-text{
    cursor:pointer;
    word-break:break-word;
}

.completed{
    text-decoration:line-through;
    opacity:.6;
}

.actions{
    display:flex;
    gap:8px;
}

.complete-btn{
    background:#22c55e;
    color:white;
    padding:6px 10px;
}

.delete-btn{
    background:#ef4444;
    color:white;
    padding:6px 10px;
}

.clear-btn{
    width:100%;
    margin-top:15px;
    padding:12px;
    background:#ef4444;
    color:white;
}

.empty{
    text-align:center;
    opacity:.8;
    padding:20px;
}

@media(max-width:600px){

    .input-group{
        flex-direction:column;
    }

    .add-btn{
        width:100%;
    }

    li{
        flex-direction:column;
        gap:10px;
        align-items:flex-start;
    }

    .actions{
        width:100%;
    }

    .actions button{
        flex:1;
    }
}
</style>
</head>
<body>

<div class="container">

    <h1>📝 To-Do List App</h1>

    <div class="input-group">
        <input
            type="text"
            id="taskInput"
            placeholder="Enter a task..."
        >
        <button
            class="add-btn"
            onclick="addTask()">
            Add
        </button>
    </div>

    <div class="filters">
        <button onclick="setFilter('all')">All</button>
        <button onclick="setFilter('active')">Active</button>
        <button onclick="setFilter('completed')">Completed</button>
    </div>

    <div class="stats">
        <span>Total Tasks: <strong id="taskCount">0</strong></span>
    </div>

    <ul id="taskList"></ul>

    <button
        class="clear-btn"
        onclick="clearAll()">
        Clear All Tasks
    </button>

</div>

<script>

const taskInput =
document.getElementById("taskInput");

const taskList =
document.getElementById("taskList");

const taskCount =
document.getElementById("taskCount");

let tasks =
JSON.parse(localStorage.getItem("tasks")) || [];

let currentFilter = "all";

renderTasks();

function saveTasks(){
    localStorage.setItem(
        "tasks",
        JSON.stringify(tasks)
    );
}

function addTask(){

    const text =
    taskInput.value.trim();

    if(text === ""){
        alert("Please enter a task");
        return;
    }

    tasks.push({
        text:text,
        completed:false
    });

    taskInput.value="";

    saveTasks();
    renderTasks();
}

function toggleTask(index){

    tasks[index].completed =
    !tasks[index].completed;

    saveTasks();
    renderTasks();
}

function deleteTask(index){

    tasks.splice(index,1);

    saveTasks();
    renderTasks();
}

function clearAll(){

    if(confirm(
        "Delete all tasks?"
    )){
        tasks = [];
        saveTasks();
        renderTasks();
    }
}

function setFilter(filter){

    currentFilter = filter;

    renderTasks();
}

function renderTasks(){

    taskList.innerHTML = "";

    let filtered =
    tasks.filter(task=>{

        if(currentFilter==="active")
            return !task.completed;

        if(currentFilter==="completed")
            return task.completed;

        return true;
    });

    if(filtered.length===0){

        taskList.innerHTML=
        `<div class="empty">
            No tasks found
        </div>`;
    }

    filtered.forEach(task=>{

        const originalIndex =
        tasks.indexOf(task);

        const li =
        document.createElement("li");

        li.innerHTML = `
            <span
            class="task-text ${
                task.completed
                ? "completed"
                : ""
            }">
            ${task.text}
            </span>

            <div class="actions">

                <button
                class="complete-btn"
                onclick="toggleTask(${originalIndex})">
                ✔
                </button>

                <button
                class="delete-btn"
                onclick="deleteTask(${originalIndex})">
                🗑
                </button>

            </div>
        `;

        taskList.appendChild(li);
    });

    taskCount.textContent =
    tasks.length;
}

taskInput.addEventListener(
    "keypress",
    function(e){

        if(e.key==="Enter"){
            addTask();
        }
    }
);

</script>

</body>
</html>