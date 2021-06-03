package com.example.writeo.controllerService.services;

import com.example.writeo.controllerService.interfaces.ISellService;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.Sell;
import com.example.writeo.repository.ArticleRepository;
import com.example.writeo.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellService implements ISellService {
    @Autowired
    private SellRepository sellRepository;


    @Override
    public List<Sell> findAll() throws JPAException {
        try{
            List<Sell> sells= sellRepository.findAll();
            if (sells.isEmpty()) return null;
            return sellRepository.findAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new JPAException();
        }
    }

    @Override
    public Sell save(Sell sell){
        if (sell != null) {
            return sellRepository.save(sell);
        }else throw new NullPointerException();
    }

    @Override
    public Optional<Sell> findById(long id) {
        if(id >= 0L){
            return sellRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(long id) {
        sellRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        sellRepository.deleteAll();
    }

    public Sell updateSell(Sell changedSell, long id){
        Optional<Sell> sellData = sellRepository.findById(id);

        if (sellData.isPresent()) {
            changedSell.setId(id);
            sellRepository.save(changedSell);
            return changedSell;
        } else {
            throw new NullPointerException();
        }
    }
}
