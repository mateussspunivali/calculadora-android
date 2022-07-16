package br.com.forumtematico.calculadora.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.forumtematico.calculadora.Historico ;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calculadora";
    private static final String TABLE_HISTORICO = "historico";

    private static final String CREATE_TABLE_HISTORICO = "CREATE TABLE " + TABLE_HISTORICO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "primeiro_numero DOUBLE, " +
            "segundo_numero DOUBLE, " +
            "operador CHAR(1), " +
            "resultado DOUBLE);";

    private static final String DROP_TABLE_HISTORICO = "DROP TABLE IF EXISTS " + TABLE_HISTORICO;

    public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HISTORICO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE_HISTORICO);
    }

    public Historico getFirstHistorico () {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "primeiro_numero", "segundo_numero", "operador", "resultado"};
        Cursor data = db.query(TABLE_HISTORICO, columns, null, null, null, null, "_id DESC");

        data.moveToFirst();
        Historico h = new Historico();
        h.setId(data.getInt(0));
        h.setPrimeiro_numero(data.getDouble(1));
        h.setSegundo_numero(data.getDouble(2));
        h.setOperecao(data.getString(3));
        h.setResultado(data.getDouble(4));
        data.close();
        db.close();
        return h;
    }

    public long createHistorico (Historico h) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("primeiro_numero", h.getPrimeiro_numero());
        cv.put("segundo_numero", h.getSegundo_numero());
        cv.put("operador", h.getOperecao());
        cv.put("resultado", h.getResultado());
        long id = db.insert(TABLE_HISTORICO, null, cv);
        db.close();
        return id;
    }
}
