package ru.greenkins.agencymejb.service;

import jakarta.ejb.Stateless;
import ru.greenkins.agencymejb.model.Flat;
import ru.greenkins.agencymejb.util.RealEstateAppraisal;

import java.util.List;


@Stateless
public class FlatComparisonServiceImpl implements FlatComparisonService {
    @Override
    public Flat getCheapest(Flat flat1, Flat flat2) {
        if (flat1 == null || flat2 == null) return null;
        int price1 = RealEstateAppraisal.appraise(flat1);
        int price2 = RealEstateAppraisal.appraise(flat2);
        return (price1 < price2) ? flat1 : flat2;
    }
}