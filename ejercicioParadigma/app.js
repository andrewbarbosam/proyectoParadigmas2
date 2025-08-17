// app.js
// Gestión de libros con Node.js usando POO y JSON
// Autor: Andrew Barbosa
// Fecha: 2025

const fs = require("fs");
const readline = require("readline");

// Clase Book: representa un libro
class Book {
    constructor(title, author, year, genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }
}

// Clase BookManager: maneja la colección de libros
class BookManager {
    constructor(filename) {
        this.filename = filename;
        this.books = this.loadBooks();
    }

    // Cargar libros desde archivo JSON
    loadBooks() {
        if (fs.existsSync(this.filename)) {
            const data = fs.readFileSync(this.filename, "utf-8");
            return JSON.parse(data);
        }
        return [];
    }

    // Guardar libros en archivo JSON
    saveBooks() {
        fs.writeFileSync(this.filename, JSON.stringify(this.books, null, 2));
    }

    // Agregar libro
    addBook(book) {
        this.books.push(book);
        this.saveBooks();
        console.log("✅ Libro agregado con éxito.");
    }

    // Listar todos los libros
    listBooks() {
        if (this.books.length === 0) {
            console.log("📚 No hay libros en la colección.");
            return;
        }
        this.books.forEach((book, index) => {
            console.log(`${index + 1}. ${book.title} - ${book.author} (${book.year}) [${book.genre}]`);
        });
    }

    // Buscar libro por título
    searchBook(title) {
        const found = this.books.filter(book => book.title.toLowerCase().includes(title.toLowerCase()));
        if (found.length === 0) {
            console.log("❌ No se encontraron libros con ese título.");
        } else {
            console.log("🔎 Resultados de búsqueda:");
            found.forEach(book => {
                console.log(`- ${book.title} - ${book.author} (${book.year}) [${book.genre}]`);
            });
        }
    }

    // Eliminar libro por título
    removeBook(title) {
        const initialLength = this.books.length;
        this.books = this.books.filter(book => book.title.toLowerCase() !== title.toLowerCase());
        if (this.books.length < initialLength) {
            this.saveBooks();
            console.log("🗑️ Libro eliminado con éxito.");
        } else {
            console.log("❌ No se encontró un libro con ese título.");
        }
    }
}

// Crear interfaz de consola
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

const manager = new BookManager("books.json");

// Mostrar menú
function showMenu() {
    console.log("\n===== MENÚ GESTIÓN DE LIBROS =====");
    console.log("1. Agregar libro");
    console.log("2. Listar libros");
    console.log("3. Buscar libro por título");
    console.log("4. Eliminar libro por título");
    console.log("5. Salir");
    rl.question("👉 Elige una opción: ", option => handleOption(option));
}

// Manejo de opciones
function handleOption(option) {
    switch (option) {
        case "1":
            rl.question("Título: ", title => {
                rl.question("Autor: ", author => {
                    rl.question("Año: ", year => {
                        rl.question("Género: ", genre => {
                            const book = new Book(title, author, year, genre);
                            manager.addBook(book);
                            showMenu();
                        });
                    });
                });
            });
            break;
        case "2":
            manager.listBooks();
            showMenu();
            break;
        case "3":
            rl.question("Ingrese título a buscar: ", title => {
                manager.searchBook(title);
                showMenu();
            });
            break;
        case "4":
            rl.question("Ingrese título a eliminar: ", title => {
                manager.removeBook(title);
                showMenu();
            });
            break;
        case "5":
            console.log("👋 Saliendo del programa...");
            rl.close();
            break;
        default:
            console.log("⚠️ Opción no válida.");
            showMenu();
    }
}

// Iniciar programa
showMenu();
