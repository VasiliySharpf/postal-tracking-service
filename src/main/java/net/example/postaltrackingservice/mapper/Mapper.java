package net.example.postaltrackingservice.mapper;


public interface Mapper<F, T> {

    T map(F object);

}
