package com.example.formulario_vistoria;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Parameter;

public class MainActivity extends AppCompatActivity {

    EditText editTextCondominio,editTextEndereco, editTextBairro, editTextArquivo;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCondominio = findViewById(R.id.editTextCondominio);
        editTextEndereco = findViewById(R.id.editTextEndereco);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextArquivo = findViewById(R.id.editTextArquivo);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String condo = editTextCondominio.getText().toString();
                String endereco = editTextEndereco.getText().toString();
                String bairro = editTextBairro.getText().toString();
                String arquivo = editTextArquivo.getText().toString();

                try {
                    createPdf(condo, endereco, bairro, arquivo);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createPdf(String condo, String endereco, String bairro, String arquivo) throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, arquivo + ".pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(0, 0, 0, 0);

        Paragraph formularioVistoria = new Paragraph("FORMULÁRIO VISTORIA").setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER);
        Paragraph info = new Paragraph("Condomínio: " + condo +
                                            "\nEndereco: " + endereco +
                                            "\nBairro: " + bairro).setTextAlignment(TextAlignment.CENTER);

        document.add(formularioVistoria);
        document.add(info);

        document.close();
        Toast.makeText(this, "Pdf criado!", Toast.LENGTH_LONG).show();
    }
}