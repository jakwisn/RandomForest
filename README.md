# Dokumentacja 

## Autorzy
Paulina Przybyłek
Jakub Wiśniewski
Dawid Przybyliński

start: 9 grudnia 2019

## Konspekt projektu

Cele projektu, jego krótka charakterystyka i opis podziału pracy zostały zawarte w pliku Konspekt.pdf. Projekt będzie realizowany zgodnie z zawartymi tam informacjami. Zaleca się zapoznanie z nim.

### @ToDo
1. W convertToDataFrame sprawdzać na końcu czy ramka danych ma jednolite typy kolumn. Jeśli nie RaiseException

### Done : 
**Klasa csvToDataFrame**    
  Celem tej klasy jest konwertowanie plików csv na Ramki danych naszego autorstwa. 
  Do konstruktora przyjmuje ścieżkę do pliku csv oraz separator użyty w pliku. 
  Ważne! Csv MUSI posiadać header, jeżeli nie będzie go miało wczytany zostanie pierwszy wiersz danych.
  
>  ***getColnames()***   
>    zmienia pierwszy wiersz na listę kolumn
    
>  ***convertToDataFrame()***   
>    Konwertuje linia po lini csv i dodaje do obiektu DataFrame. 

**Klasa DataFrame**   
  Klasa ma za zadanie być naszym głównym narzędziem do posługiwania się danymi. Jednolita, z tymi samymi typami danych w kolumnach będzie świetną i wygodną alternatywą dla R-owych data Frame i Pythonowych Pandasów. 
  
>   ***convertToNumeric()***   
>     Dla każdej kolumny sprawdza czy jest Stringiem, jeżeli tak, to każdy odmienny string zamienia na inną liczbę. Operacje wykonuje na istniejącym DataFrame. Zwraca void. 


