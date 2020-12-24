# k8s-file-processor

Instalar na máquina:

```
jdk-11 (usado openjdk zulu)
maven 3.6.3
docker
activemq porta padrão (usando docker)
cassandra porta padrão (usando docker)
```

## Rodar os serviços

* batch-file-chunk-worker (quantas instancias quiser, quanto mais melhor será a performance)
* batch-file-reader (somente 1 instancia, definir a variável maven "homepath.dir" na linha de comando, dentro desse caminho deve haver uma pasta /data/in com os arquivos de input)
* salesman-api (quantas instancias quiser para escalar)

Na raiz de cada projeto execute:

```
mvn spring-boot:run
```

Para definir variável maven execute (não incluir /data/in neste endereço):

```
mvn spring-boot:run -Dhomepath.dir=/YOUR/HOME/PATH/DIR/
```

Alternativamente pode ser usada a variável de ambiente HOMEPATH (linux) ao invés do parâmetro maven.
