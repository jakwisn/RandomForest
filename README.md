# Dokumentacja 

## Autorzy
Paulina Przybyłek
Jakub Wiśniewski
Dawid Przybyliński

start: 9 grudnia 2019

## @ToDo


## Done : 
Klasa csvToDataFrame

  Celem tej klasy jest konwertowanie plików csv na Ramki danych naszego autorstwa. 
  Do konstruktora przyjmuje ścieżkę do pliku csv oraz separator użyty w pliku. 
  Ważne! Csv MUSI posiadać header, jeżeli nie będzie go miało wczytany zostanie pierwszy wiersz danych.
  
  *getColnames()* 
    zmienia pierwszy wiersz na listę kolumn
    
  *convertToDataFrame()* 
    Konwertuje linia po lini csv i dodaje do obiektu DataFrame. 

Klasa DataFrame
  
  Klasa ma za zadanie być naszym głównym narzędziem do posługiwania się danymi. Jednolita, z tymi samymi typami danych w kolumnach będzie świetną i wygodną alternatywą dla R-owych data Frame i Pythonowych Pandasów. 
  



