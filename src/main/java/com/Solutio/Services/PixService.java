package com.Solutio.Services;


import com.Solutio.Dtos.PixDto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Pix;
import com.Solutio.Repositories.PixRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PixService {


    @Autowired
    PixRepository pixRepository;

    public Pix createPix(PixDto pixDto, Charge charge){
        Pix pix = new Pix();
        BeanUtils.copyProperties(pixDto,pix);
        pix.setCharge(charge);
        return pixRepository.save(pix);
    }
}
