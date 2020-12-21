# k8s-file-processor

Instalar na máquina:

```
jdk-11 (usado openjdk zulu)
maven 3.6.3
docker
rabbitmq porta padrão (usando docker)
cassandra porta padrão (usando docker)
```

## Rodar os serviços 
    * batch-file-chunk-workder (quantas instancias quiser, quanto mais melhor será a performance)
    * batch-file-reader (somente 1 instancia, definir a variável de ambiente "HOME_PATH" na linha de comando, dentro desse caminho deve haver uma pasta /data/in com os arquivos de input)
    * salesman-api (quantas instancias quiser para escalar)

Na raiz de cada projeto execute:

```
mvn spring-boot:run
```

Para definir variável de ambiente execute:

```
mvn spring-boot:run -DHOME_PATH=/YOUR/HOME/PATH/DIR/
```

Melhorias

implementar as regras de negócio de clientes e vendas
cada contexto na sua aplicação
dockerizar
escalar via kubernetes