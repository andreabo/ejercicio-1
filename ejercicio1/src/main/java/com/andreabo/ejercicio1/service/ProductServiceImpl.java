package com.andreabo.ejercicio1.service;

import com.andreabo.ejercicio1.dto.GenericDTO;
import com.andreabo.ejercicio1.model.Product;
import com.andreabo.ejercicio1.repository.ProductRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Override
    public boolean updateProduct(Long id, Product product) {
        Product toUpdate = productRepository.findOne(id);

        return Optional.ofNullable(toUpdate).map(p->{
            p.setStock(product.getStock());
            p.setName(product.getName());
            productRepository.save(p);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Product> getInventory() {
        return productRepository.findAll();
    }

    @Override
    public boolean deleteProduct(Long id) {
        productRepository.delete(id);
        return true;
    }

    @Override
    public Long addProduct(GenericDTO genericDTO) {
        Product toSave = new Product().setName(genericDTO.getName()).setStock(genericDTO.getStock());
        return productRepository.save(toSave).getId();
    }

    @Override
    public ByteArrayInputStream generateReport() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<Product> products = productRepository.findAll();

        try {

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{1, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Stock", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            products.forEach( product ->{
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(product.getId().toString()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(product.getName()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(product.getStock())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
            });

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();

        } catch (DocumentException ex) {
            log.error(ex.getMessage(),ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
