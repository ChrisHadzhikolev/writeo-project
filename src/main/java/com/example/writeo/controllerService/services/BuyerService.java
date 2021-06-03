package com.example.writeo.controllerService.services;

import com.example.writeo.controllerService.interfaces.IBuyerService;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.Buyer;
import com.example.writeo.repository.ArticleRepository;
import com.example.writeo.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyerService implements IBuyerService {
    @Autowired
    private BuyerRepository buyerRepository;


    @Override
    public List<Buyer> findAll() throws JPAException {
        try{
            List<Buyer> buyers= buyerRepository.findAll();
            if (buyers.isEmpty()) return null;
            return buyers;
        }catch (Exception e){
            throw new JPAException();
        }
    }

    @Override
    public Buyer save(Buyer buyer){
        if (buyer != null) {
            return buyerRepository.save(buyer);
        }else throw new NullPointerException();
    }

    @Override
    public Optional<Buyer> findById(long id) {
        if(id >= 0L){
            return buyerRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(long id) {
        buyerRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        buyerRepository.deleteAll();
    }

    public Buyer updateBuyer(Buyer changedBuyer, long id){
        Optional<Buyer> buyerData = buyerRepository.findById(id);

        if (buyerData.isPresent()) {
            changedBuyer.setId(id);
            buyerRepository.save(changedBuyer);
            return changedBuyer;
        } else {
            throw new NullPointerException();
        }
    }
}
