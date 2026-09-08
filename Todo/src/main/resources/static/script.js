
    const inputBox = document.getElementById('input-box');
    const listContainer = document.getElementById('list-container');

    // ── 1. Alle Todos laden beim Start ──────────────────
    async function loadTodos() {
    const response = await fetch("/api/todo");
    const todos = await response.json();

    listContainer.innerHTML = "";  // Liste leeren

    todos.forEach(todo => {
    let li = document.createElement('li');
    li.innerHTML = todo.title;          // ← title aus DB
    li.setAttribute('data-id', todo.id); // ← id aus DB speichern

    // completed → checked Klasse setzen
    if (todo.completed) {
    li.classList.add('checked');
}

    let span = document.createElement('span');
    span.innerHTML = "\u00d7";
    li.appendChild(span);

    listContainer.appendChild(li);
});
}

    // ── 2. Neues Todo erstellen ──────────────────────────
    async function addTask() {
    if (inputBox.value === '') {
    alert("Du musst etwas schreiben");
    return;
}

    await fetch("/api/todo", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ title: inputBox.value })
    //                     ↑ muss mit TodoRequest übereinstimmen
});

    inputBox.value = '';
    loadTodos();  // Liste neu laden
}

    // ── 3. Klick auf Li (complete) oder Span (löschen) ──
    listContainer.addEventListener('click', async function(e) {

    // Todo als erledigt markieren
    if (e.target.tagName === "LI") {
    const id = e.target.getAttribute('data-id');
    await fetch(`/api/todo/${id}/complete`, {
    method: "PATCH"
});
    loadTodos();
}

    // Todo löschen
    else if (e.target.tagName === "SPAN") {
    const id = e.target.parentElement.getAttribute('data-id');
    await fetch(`/api/todo/${id}`, {
    method: "DELETE"
});
    loadTodos();
}
});

    // ── 4. Beim Start alle Todos laden ──────────────────
    loadTodos();
