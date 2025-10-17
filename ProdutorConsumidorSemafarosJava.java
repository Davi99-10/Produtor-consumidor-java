package ProblemaPC;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.Random;

// DAVI MOREIRA LIMA; ALLEF KAZUMI SAKURADA DE SANTANA; VICTOR GUIMARÃES SILVA; GABRIEL DOS SANTOS PEREIRA;

// A classe principal que simula a execução

public class ProdutorConsumidorSemafarosJava {

    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(5); // buffer_size = 5

    public static void main(String[] args) {
    	
        Thread produtorThread = new Thread(new Produtor());
        Thread consumidorThread = new Thread(new Consumidor());

        produtorThread.start();
        consumidorThread.start();
    }

    // A Thread Produtor
    
    static class Produtor implements Runnable {
        private final Random random = new Random();

        @Override
        public void run() {
            while (true) { 
                try {
                    int item = random.nextInt(100) + 1; 
                    
                    buffer.put(item); 
                    
                    
                    System.out.println("Produtor: Produzido item: " + item + " | Tamanho: " + buffer.size());
                    
                    Thread.sleep(random.nextInt(1000) + 100); 

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    // A Thread Consumidor
    
    static class Consumidor implements Runnable {
        private final Random random = new Random();

        @Override
        public void run() {
            while (true) { 
                try {
                	
                    int item = buffer.take(); 
                                        
                    System.out.println("Consumidor: Consumido item: " + item + " | Tamanho: " + buffer.size());

                    Thread.sleep(random.nextInt(1500) + 500);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}