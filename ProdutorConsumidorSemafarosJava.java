package ProblemaPC;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.Random;

// A classe principal que simula a execução
public class ProdutorConsumidorSemafarosJava {

    // Simula a lógica de semáforos (mutex, full, empty)
    // - O 'buffer' gerencia:
    //   - O espaço livre (Semáforo 'empty' em Python)
    //   - A contagem de itens (Semáforo 'full' em Python)
    //   - A exclusão mútua (Semáforo 'mutex' em Python)
    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(5); // buffer_size = 5

    public static void main(String[] args) {
        // Cria e inicia as threads produtor e consumidor
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
            while (true) { // while True:
                try {
                    int item = random.nextInt(100) + 1; // item = random.randint(1, 100)
                    
                    // empty.acquire() e mutex.acquire() e buffer.append(item)
                    // -> O método put() faz tudo isso. Ele bloqueia se o buffer estiver cheio (empty)
                    buffer.put(item); 
                    
                    // mutex.release() e full.release()
                    // -> O put() libera implicitamente o "full" para o consumidor
                    
                    System.out.println("Produtor: Produzido item: " + item + " | Tamanho: " + buffer.size());
                    
                    // time.sleep(random.random())
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
            while (true) { // while True:
                try {
                    // full.acquire() e mutex.acquire()
                    // -> O método take() faz tudo isso. Ele bloqueia se o buffer estiver vazio (full)
                    int item = buffer.take(); 
                    
                    // item = buffer.pop(0) - a fila remove o mais antigo

                    // mutex.release() e empty.release()
                    // -> O take() libera implicitamente o "empty" para o produtor
                    
                    System.out.println("Consumidor: Consumido item: " + item + " | Tamanho: " + buffer.size());

                    // time.sleep(random.random())
                    Thread.sleep(random.nextInt(1500) + 500);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}