package com.andreabo.ejercicio1.service;

import com.andreabo.ejercicio1.dto.GenericDTO;
import com.andreabo.ejercicio1.model.AppUser;
import com.andreabo.ejercicio1.repository.UserRepository;
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
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean updateUser(Long id, AppUser appUser) {

        AppUser toUpdate = userRepository.findOne(id);

        return Optional.ofNullable(toUpdate).map(u->{
            u.setName(appUser.getName());
            userRepository.save(u);
            return true;
        }).orElse(false);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepository.delete(id);
        return true;
    }

    @Override
    public Long addUser(GenericDTO genericDTO) {
        AppUser toSave = new AppUser().setName(genericDTO.getName());
        return userRepository.save(toSave).getId();
    }

    @Override
    public ByteArrayInputStream generateReport() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<AppUser> users = userRepository.findAll();

        try {

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{1, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            users.forEach( product ->{
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
