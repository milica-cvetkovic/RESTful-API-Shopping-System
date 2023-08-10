/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.*;

/**
 *
 * @author milic
 */
public class Klijent {
    
    public void choice(){
        
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        String nazivGrada, nazivDrzave;
        String username, password, imeKorisnika, prezimeKorisnika, novacKorisnika, adresaKorisnika, gradKorisnika;
        String ulica, brojAdrese;
        String nazivKategorije;
        String nazivArtikla, opisArtikla, cenaArtikla, popust;
        String kolicinaArtikla;
        String sumaTransakcije, vremeTransakcije;
        String ukupnaCenaNarudzbine, vremeNarudzbine;
        String naziv, opis, cena, kategorija;
        
        System.out.println("MENU\n");
        System.out.println("1. Kreiranje grada");
        System.out.println("2. Kreiranje korisnika");
        System.out.println("3. Dodavanje novca korisniku");
        System.out.println("4. Promena adrese i grada za korisnika");
        System.out.println("5. Kreiranje kategorije");
        System.out.println("6. Kreiranje artikla");
        System.out.println("7. Menjanje cene artikla");
        System.out.println("8. Postavljanje popusta za artikal");
        System.out.println("9. Dodavanje artikla u odredjenoj kolicini u korpu");
        System.out.println("10. Brisanje artikla u odredjenoj kolicini iz korpe");
        System.out.println("11. Placanje, koje obuhvata kreiranje transakcije, kreiranje narudzbine sa njenim stavkama, i brisanje sadrzaja iz korpe");
        System.out.println("12. Dohvatanje svih gradova");
        System.out.println("13. Dohvatanje svih korisnika");
        System.out.println("14. Dohvatanje svih kategorija");
        System.out.println("15. Dohvatanje svih artikala koje prodaje korisnik koji je poslao zahtev");
        System.out.println("16. Dohvatanje sadrzaja korpe korisnika koji je poslao zahtev");
        System.out.println("17. Dohvatanje svih narudzbina korisnika koji je poslao zahtev");
        System.out.println("18. Dohvatanje svih narudzbina");
        System.out.println("19. Dohvatanje svih transakcija");
       
        int choice;
        
        String urlAddress;
        URL url;
        HttpURLConnection httpConnection;
        String params;
        OutputStream outputStream;
        int responseCode;
        BufferedReader in;
        String inputLine;
        StringBuffer response;
        Pattern pattern;
        Matcher matcher;
        
        while(true){
            
            try {
                System.out.println("Izaberite opciju: ");
                choice = Integer.parseInt(input.readLine());
                
                switch(choice){
                    
                    case 1:
                        
                        System.out.println("Naziv grada: ");
                        nazivGrada = input.readLine();
                        
                        System.out.println("Naziv drzave: ");
                        nazivDrzave = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/gradovi";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();

                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "naziv=" + nazivGrada + "&" + "drzava=" + nazivDrzave;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();

                        System.out.println(response.toString());
                        
                        break;
                    case 2:
                        
                        System.out.println("Username: ");
                        username = input.readLine();
                        
                        System.out.println("Password: ");
                        password = input.readLine();
                        
                        System.out.println("Ime: ");
                        imeKorisnika = input.readLine();
                        
                        System.out.println("Prezime: ");
                        prezimeKorisnika = input.readLine();
                        
                        System.out.println("Novac: ");
                        novacKorisnika = input.readLine();
                        
                        System.out.println("Adresa: ");
                        adresaKorisnika = input.readLine();
                        
                        System.out.println("Grad: ");
                        gradKorisnika = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password + "&" + "ime=" + imeKorisnika 
                                + "&" + "prezime=" + prezimeKorisnika + "&" + "novac=" + novacKorisnika + "&" + "adresa=" + adresaKorisnika
                                + "&" + "grad=" + gradKorisnika;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();

                        System.out.println(response.toString());
                        
                        break;
                    case 3:
                        System.out.println("Unesi username korisnika: ");
                        username = input.readLine();
                        System.out.println("Unesi novac: ");
                        String novac =input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/novac";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "novac=" + novac;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();

                        System.out.println(response.toString());
                        
                        break;
                        
                    case 4:
                        System.out.println("Unesi username korisnika: ");
                        username = input.readLine();
                        System.out.println("Unesi adresu: ");
                        adresaKorisnika = input.readLine();
                        System.out.println("Unesi grad: ");
                        gradKorisnika = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/promena";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "adresa=" + adresaKorisnika + "&" + "grad=" + gradKorisnika; 
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        System.out.println(response.toString());
                        
                        break;
                    case 12:
                        
                        urlAddress = "http://localhost:8080/server/resources/gradovi";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("GET");
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            
                            response.append(inputLine);
                        }
                        in.close();
                        
                        pattern = Pattern.compile("\"(.*?)\"");
                        matcher = pattern.matcher(response.toString());
                        
                        
                        System.out.println(response.toString());
                        
                        while(matcher.find()){
                            
                            
                            String result = matcher.group(1);
                            String[] split = result.split(";");  
                            
                            System.out.println("Naziv: " + split[0] + ", Drzava: " + split[1]);
                            
                            
                        }
  
                        break;
                    case 13:
                        urlAddress = "http://localhost:8080/server/resources/korisnici";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("GET");
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            
                            response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        pattern = Pattern.compile("\"(.*?)\"");
                        matcher = pattern.matcher(response.toString());
                        
                        while(matcher.find()){
                            
                            
                            String result = matcher.group(1);
                            String[] split = result.split(";");  
                            
                            System.out.println("Username: " + split[0] + ", Ime: " + split[1] + ", Prezime: " + split[2]
                            + ", Novac: " + split[3] + ", Adresa: " + split[4] + ", Grad: " + split[5]);
                            
                            
                        }
  
                        break;
                    case 5:
                        System.out.println("Unesi naziv kategorije: ");
                        naziv = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/kategorije";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "naziv=" + naziv;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();

                        System.out.println(response.toString());
                        
                        break;
                    case 6:
                        
                        System.out.println("Unesi username: ");
                        username = input.readLine();
                        System.out.println("Unesi password: ");
                        password = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/autentikacija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();

                        System.out.println(response.toString());
                        
                        if(responseCode == 200){
                            System.out.println("Unesi naziv artikla: ");
                            naziv = input.readLine();
                            System.out.println("Unesi opis artikla: ");
                            opis = input.readLine();
                            System.out.println("Unesi cenu artikla: ");
                            cena = input.readLine();
                            System.out.println("Unesi popust za artikal: ");
                            popust = input.readLine();
                            System.out.println("Unesi kategoriju artikla: ");
                            kategorija = input.readLine();

                            urlAddress = "http://localhost:8080/server/resources/artikli";

                            url = new URL(urlAddress);

                            httpConnection = (HttpURLConnection) url.openConnection();

                            httpConnection.setRequestMethod("POST");
                            httpConnection.setDoOutput(true);

                            params = "naziv=" + naziv + "&" + "opis=" + opis + "&" + "cena=" + cena + "&" + "popust=" + popust
                                    + "&" + "kategorija=" + kategorija + "&" + "username=" + username;

                            outputStream = httpConnection.getOutputStream();

                            outputStream.write(params.getBytes());
                            outputStream.flush();
                            outputStream.close();

                            responseCode = httpConnection.getResponseCode();
                            System.out.println("Response code: " + responseCode);

                            in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                            }
                            in.close();

                            System.out.println(response.toString());
                        }
                        
                        break;
                    case 7:
                        
                        System.out.println("Unesi username: ");
                        username = input.readLine();
                        System.out.println("Unesi password: ");
                        password = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/autentikacija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        if(responseCode == 200){

                            System.out.println("Unesi naziv artikla: ");
                            naziv = input.readLine();
                            System.out.println("Unesi cenu za dodavanje artiklu: ");
                            cena = input.readLine();

                            urlAddress = "http://localhost:8080/server/resources/artikli/cena";

                            url = new URL(urlAddress);

                            httpConnection = (HttpURLConnection) url.openConnection();

                            httpConnection.setRequestMethod("POST");
                            httpConnection.setDoOutput(true);

                            params = "naziv=" + naziv + "&" + "cena=" + cena + "&" + "username=" + username;

                            outputStream = httpConnection.getOutputStream();

                            outputStream.write(params.getBytes());
                            outputStream.flush();
                            outputStream.close();

                            responseCode = httpConnection.getResponseCode();
                            System.out.println("Response code: " + responseCode);

                            in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                            }
                            in.close();

                            System.out.println(response.toString());
                        }
                        break;
                    case 8:
                        
                        System.out.println("Unesi username: ");
                        username = input.readLine();
                        System.out.println("Unesi password: ");
                        password = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/autentikacija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        if(responseCode == 200){
                            System.out.println("Unesi naziv artikla: ");
                            naziv = input.readLine();
                            System.out.println("Unesi popust: ");
                            popust = input.readLine();

                            urlAddress = "http://localhost:8080/server/resources/artikli/popust";

                            url = new URL(urlAddress);

                            httpConnection = (HttpURLConnection) url.openConnection();

                            httpConnection.setRequestMethod("POST");
                            httpConnection.setDoOutput(true);

                            params = "naziv=" + naziv + "&" + "popust=" + popust + "&" + "username=" + username;

                            outputStream = httpConnection.getOutputStream();

                            outputStream.write(params.getBytes());
                            outputStream.flush();
                            outputStream.close();

                            responseCode = httpConnection.getResponseCode();
                            System.out.println("Response code: " + responseCode);

                            in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                            }
                            in.close();

                            System.out.println(response.toString());
                        }
                        break;
                    case 9:
                        System.out.println("Unesi username: ");
                        username = input.readLine();
                        System.out.println("Unesi password: ");
                        password = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/autentikacija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        if(responseCode == 200){
                            
                            System.out.println("Unesi naziv artikla: ");
                            nazivArtikla = input.readLine();
                            System.out.println("Unesi kolicinu: ");
                            kolicinaArtikla = input.readLine();
                            
                            urlAddress = "http://localhost:8080/server/resources/korpe";
                        
                            url = new URL(urlAddress);

                            httpConnection = (HttpURLConnection) url.openConnection();

                            httpConnection.setRequestMethod("POST");
                            httpConnection.setDoOutput(true);

                            params = "naziv=" + nazivArtikla + "&" + "kolicina=" + kolicinaArtikla + "&" + "username=" + username; 

                            outputStream = httpConnection.getOutputStream();

                            outputStream.write(params.getBytes());
                            outputStream.flush();
                            outputStream.close();

                            responseCode = httpConnection.getResponseCode();
                            System.out.println("Response code: " + responseCode);

                            in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                            }
                            in.close();

                            System.out.println(response.toString());

                            
                        }
                        break;
                    case 10:
                        System.out.println("Unesi username: ");
                        username = input.readLine();
                        System.out.println("Unesi password: ");
                        password = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/autentikacija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        if(responseCode == 200){
                            
                            System.out.println("Unesi naziv artikla: ");
                            nazivArtikla = input.readLine();
                            System.out.println("Unesi kolicinu: ");
                            kolicinaArtikla = input.readLine();
                            
                            urlAddress = "http://localhost:8080/server/resources/korpe/obrisi";
                        
                            url = new URL(urlAddress);

                            httpConnection = (HttpURLConnection) url.openConnection();

                            httpConnection.setRequestMethod("POST");
                            httpConnection.setDoOutput(true);

                            params = "naziv=" + nazivArtikla + "&" + "kolicina=" + kolicinaArtikla + "&" + "username=" + username; 

                            outputStream = httpConnection.getOutputStream();

                            outputStream.write(params.getBytes());
                            outputStream.flush();
                            outputStream.close();

                            responseCode = httpConnection.getResponseCode();
                            System.out.println("Response code: " + responseCode);

                            in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                            }
                            in.close();

                            System.out.println(response.toString());

                            
                        }
                        break;
                    case 14:
                        urlAddress = "http://localhost:8080/server/resources/kategorije";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("GET");
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            
                            response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        pattern = Pattern.compile("\"(.*?)\"");
                        matcher = pattern.matcher(response.toString());

                        while(matcher.find()){


                            String result = matcher.group(1); 

                            System.out.println("Naziv artikla: " + result);


                        }
  
                        break;
                    case 15:
                        System.out.println("Unesi username: ");
                        username = input.readLine();
                        System.out.println("Unesi password: ");
                        password = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/autentikacija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        if(responseCode == 200){
                            
                            urlAddress = "http://localhost:8080/server/resources/artikli/prodaja";
                        
                            url = new URL(urlAddress);

                            httpConnection = (HttpURLConnection) url.openConnection();

                            httpConnection.setRequestMethod("POST");
                            httpConnection.setDoOutput(true);

                            params = "username=" + username; 

                            outputStream = httpConnection.getOutputStream();

                            outputStream.write(params.getBytes());
                            outputStream.flush();
                            outputStream.close();

                            responseCode = httpConnection.getResponseCode();
                            System.out.println("Response code: " + responseCode);

                            in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                            }
                            in.close();

                            System.out.println(response.toString());
                            
                            pattern = Pattern.compile("\"(.*?)\"");
                            matcher = pattern.matcher(response.toString());

                            while(matcher.find()){
                            
                            
                                String result = matcher.group(1);

                                System.out.println("Naziv artikla: " + result);


                            }

                            
                        }
                        break;
                    case 11:
                        System.out.println("Unesi username: ");
                        username = input.readLine();
                        System.out.println("Unesi password: ");
                        password = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/autentikacija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        if(responseCode == 200){
                            
                            urlAddress = "http://localhost:8080/server/resources/transakcije/placanje";
                        
                            url = new URL(urlAddress);

                            httpConnection = (HttpURLConnection) url.openConnection();

                            httpConnection.setRequestMethod("POST");
                            httpConnection.setDoOutput(true);

                            params = "username=" + username; 

                            outputStream = httpConnection.getOutputStream();

                            outputStream.write(params.getBytes());
                            outputStream.flush();
                            outputStream.close();

                            responseCode = httpConnection.getResponseCode();
                            System.out.println("Response code: " + responseCode);

                            in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                            }
                            in.close();

                            System.out.println(response.toString());
                            
                            
                        }
                        break;
                    case 16:
                        System.out.println("Unesi username: ");
                        username = input.readLine();
                        System.out.println("Unesi password: ");
                        password = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/autentikacija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        if(responseCode == 200){
                            
                            urlAddress = "http://localhost:8080/server/resources/korpe/sadrzaj";
                        
                            url = new URL(urlAddress);

                            httpConnection = (HttpURLConnection) url.openConnection();

                            httpConnection.setRequestMethod("POST");
                            httpConnection.setDoOutput(true);

                            params = "username=" + username; 

                            outputStream = httpConnection.getOutputStream();

                            outputStream.write(params.getBytes());
                            outputStream.flush();
                            outputStream.close();

                            responseCode = httpConnection.getResponseCode();
                            System.out.println("Response code: " + responseCode);

                            in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                            }
                            in.close();

                            pattern = Pattern.compile("\"(.*?)\"");
                            matcher = pattern.matcher(response.toString());

                            while(matcher.find()){
                            
                            
                                String result = matcher.group(1);
                                String[] split = result.split(";");  

                                System.out.println("Naziv artikla: " + split[0] + ", Kolicina: " + split[1]);


                            }

                        }
                        break;
                    case 17:
                        System.out.println("Unesi username: ");
                        username = input.readLine();
                        System.out.println("Unesi password: ");
                        password = input.readLine();
                        
                        urlAddress = "http://localhost:8080/server/resources/korisnici/autentikacija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        
                        params = "username=" + username + "&" + "password=" + password;
                        
                        outputStream = httpConnection.getOutputStream();
                        
                        outputStream.write(params.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();
                        
                        System.out.println(response.toString());
                        
                        if(responseCode == 200){
                            
                            urlAddress = "http://localhost:8080/server/resources/transakcije/naruci";
                        
                            url = new URL(urlAddress);

                            httpConnection = (HttpURLConnection) url.openConnection();

                            httpConnection.setRequestMethod("POST");
                            httpConnection.setDoOutput(true);

                            params = "username=" + username; 

                            outputStream = httpConnection.getOutputStream();

                            outputStream.write(params.getBytes());
                            outputStream.flush();
                            outputStream.close();

                            responseCode = httpConnection.getResponseCode();
                            System.out.println("Response code: " + responseCode);

                            in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                            }
                            in.close();
                            
                            System.out.println(response.toString());

                            pattern = Pattern.compile("\"(.*?)\"");
                            matcher = pattern.matcher(response.toString());

                            while(matcher.find()){
                            
                            
                                String result = matcher.group(1);
                                String[] split = result.split(";");  

                                System.out.println("Ukupna cena: " + split[0] + ", Vreme: " + split[1]);
                            
                            
                            }

                        }
                        break;
                    case 18:
                        urlAddress = "http://localhost:8080/server/resources/transakcije/narudzbine";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("GET");
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            
                            response.append(inputLine);
                        }
                        in.close();
                        
                        pattern = Pattern.compile("\"(.*?)\"");
                        matcher = pattern.matcher(response.toString());
                        
                        while(matcher.find()){
                            
                            
                            String result = matcher.group(1);
                            String[] split = result.split(";");  
                            
                            System.out.println("Username: " + split[0] + ", Ukupna cena: " + split[1] + ", Vreme: " + split[2]);
                            
                           
                        }
  
                        break;
                    case 19:
                        urlAddress = "http://localhost:8080/server/resources/transakcije/transakcija";
                        
                        url = new URL(urlAddress);
                        
                        httpConnection = (HttpURLConnection) url.openConnection();
                        
                        httpConnection.setRequestMethod("GET");
                        
                        responseCode = httpConnection.getResponseCode();
                        System.out.println("Response code: " + responseCode);
                        
                        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            
                            response.append(inputLine);
                        }
                        in.close();
                        
                        pattern = Pattern.compile("\"(.*?)\"");
                        matcher = pattern.matcher(response.toString());
                        
                        while(matcher.find()){
                            
                            
                            String result = matcher.group(1);
                            String[] split = result.split(";");  
                            
                            System.out.println("Username: " + split[0] + ", Suma: " + split[1] + ", Vreme: " + split[2]);
                            
                           
                        }
  
                        break;
                        
                }
            } catch (IOException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public static void main(String[] args) {
        
        Klijent klijent = new Klijent();
        klijent.choice();
        
    }
    
    
    
}


//SET FOREIGN_KEY_CHECKS=0;
