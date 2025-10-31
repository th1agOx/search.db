from flask import Flask, jsonify, request
from models import init_db, inserir_produto, listar_produtos

app = Flask(__name__)

# Inicializa banco automaticamente ao iniciar
init_db()

@app.route("/")
def root():
    return jsonify({"status": "API Flask SQLite rodando ✅"}), 200

@app.route("/produtos", methods=["GET"])
def get_produtos():
    produtos = listar_produtos()
    return jsonify(produtos), 200

@app.route("/produtos", methods=["POST"])
def add_produto():
    data = request.get_json()
    nome = data.get("nome")
    preco = data.get("preco")

    if not nome or preco is None:
        return jsonify({"erro": "Campos inválidos"}), 400

    inserir_produto(nome, preco)
    return jsonify({"mensagem": "Produto adicionado com sucesso"}), 201

if __name__ == "__main__":
    app.run(host="127.0.0.1", port=5000, debug=True)