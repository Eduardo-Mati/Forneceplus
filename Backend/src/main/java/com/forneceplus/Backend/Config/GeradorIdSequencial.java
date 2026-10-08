package com.forneceplus.Backend.Config;

import com.forneceplus.Backend.Entities.Contador;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

// Antes de salvar qualquer documento, se o @Id (String) estiver vazio,
// preenche com o próximo número da sequência da coleção ("1", "2", "3"...).
@Component
public class GeradorIdSequencial implements BeforeConvertCallback<Object> {

    private final MongoOperations mongoOperations;

    // @Lazy evita dependência circular: o MongoTemplate também carrega os callbacks.
    public GeradorIdSequencial(@Lazy MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Object onBeforeConvert(Object entidade, String colecao) {
        if (entidade instanceof Contador) {
            return entidade;
        }

        MongoPersistentEntity<?> entidadePersistente = mongoOperations.getConverter()
            .getMappingContext().getPersistentEntity(entidade.getClass());
        if (entidadePersistente == null || !entidadePersistente.hasIdProperty()) {
            return entidade;
        }

        MongoPersistentProperty propriedadeId = entidadePersistente.getRequiredIdProperty();
        if (!String.class.equals(propriedadeId.getType())) {
            return entidade;
        }

        PersistentPropertyAccessor<?> acessor = entidadePersistente.getPropertyAccessor(entidade);
        Object idAtual = acessor.getProperty(propriedadeId);
        if (idAtual != null && !idAtual.toString().isBlank()) {
            return entidade; // Já tem ID: é uma atualização.
        }

        acessor.setProperty(propriedadeId, String.valueOf(proximoValor(colecao)));
        return acessor.getBean();
    }

    // Incrementa o contador de forma atômica no banco (seguro com requisições simultâneas).
    private long proximoValor(String colecao) {
        Contador contador = mongoOperations.findAndModify(
            Query.query(Criteria.where("_id").is(colecao)),
            new Update().inc("sequencia", 1),
            FindAndModifyOptions.options().returnNew(true).upsert(true),
            Contador.class);
        return contador.getSequencia();
    }
}
