import sqlite3

DB_NAME = "database.db"

def init_db():
    """Inicializa o banco SQLite e cria a tabela de produtos."""
    conn = sqlite3.connect(DB_NAME)
    cursor = conn.cursor()
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS produtos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT NOT NULL,
            preco REAL NOT NULL
        );
    """)
    conn.commit()
    conn.close()


def inserir_produto(nome, preco):
    conn = sqlite3.connect(DB_NAME)
    cursor = conn.cursor()
    cursor.execute("INSERT INTO produtos (nome, preco) VALUES (?, ?)", (nome, preco))
    conn.commit()
    conn.close()


def listar_produtos():
    conn = sqlite3.connect(DB_NAME)
    cursor = conn.cursor()
    cursor.execute("SELECT id, nome, preco FROM produtos ORDER BY id DESC")
    produtos = cursor.fetchall()
    conn.close()
    return [{"id": p[0], "nome": p[1], "preco": p[2]} for p in produtos]