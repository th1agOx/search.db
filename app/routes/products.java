URL url = new URL("http://seu_servidor:5000/produtos");
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("GET");

public class ProdutoDbHelper extends SQLiteOpenHelper {
    
    

    private static final String DATABASE_NAME = "produtos.db";
    private static final int DATABASE_VERSION = 1;
    
    private static final String TABLE_PRODUTOS = "produtos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_PRECO = "preco";
    
    private static final String CREATE_TABLE = 
        "CREATE TABLE " + TABLE_PRODUTOS + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_NOME + " TEXT NOT NULL, " +
        COLUMN_PRECO + " REAL NOT NULL)";
    
    public ProdutoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTOS);
        onCreate(db);
    }
    
    public long inserirProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, produto.getNome());
        values.put(COLUMN_PRECO, produto.getPreco());
        
        long id = db.insert(TABLE_PRODUTOS, null, values);
        db.close();
        return id;
    }
    
    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(
            TABLE_PRODUTOS,
            new String[]{COLUMN_ID, COLUMN_NOME, COLUMN_PRECO},
            null, null, null, null,
            COLUMN_ID + " DESC"
        );
        
        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.setId(cursor.getInt(0));
                produto.setNome(cursor.getString(1));
                produto.setPreco(cursor.getDouble(2));
                produtos.add(produto);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return produtos;
    }
}