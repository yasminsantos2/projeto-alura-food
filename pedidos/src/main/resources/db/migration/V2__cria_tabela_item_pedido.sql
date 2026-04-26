CREATE TABLE IF NOT EXISTS item_do_pedido (
  id BIGSERIAL PRIMARY KEY,
  quantidade integer NOT NULL,
  descricao varchar(255) DEFAULT NULL,
  pedido_id bigint NOT NULL,
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);