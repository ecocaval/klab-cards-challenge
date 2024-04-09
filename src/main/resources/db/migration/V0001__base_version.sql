CREATE TABLE player (
   id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_player PRIMARY KEY (id)
);

CREATE TABLE card (
   id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   rank VARCHAR(2) NOT NULL,
   deck_of_cards_rank VARCHAR(10) NOT NULL,
   value INTEGER NOT NULL,
   CONSTRAINT pk_card PRIMARY KEY (id)
);

CREATE TABLE game (
   id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   CONSTRAINT pk_game PRIMARY KEY (id)
);

CREATE TABLE game_players (
  game_id UUID NOT NULL,
   player_id UUID NOT NULL
);

CREATE TABLE game_winners (
  game_id UUID NOT NULL,
   player_id UUID NOT NULL
);

CREATE TABLE hand (
   id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   player_id UUID NOT NULL,
   game_id UUID NOT NULL,
   score INTEGER NOT NULL,
   CONSTRAINT pk_hand PRIMARY KEY (id)
);

CREATE TABLE hand_cards (
   card_id UUID NOT NULL,
   hand_id UUID NOT NULL
);

ALTER TABLE player ADD CONSTRAINT uc_player_name UNIQUE (name);

ALTER TABLE card ADD CONSTRAINT uc_card_rank UNIQUE (rank);

ALTER TABLE game_players ADD CONSTRAINT fk_gampla_on_game FOREIGN KEY (game_id) REFERENCES game (id);

ALTER TABLE game_players ADD CONSTRAINT fk_gampla_on_player FOREIGN KEY (player_id) REFERENCES player (id);

ALTER TABLE game_winners ADD CONSTRAINT fk_gamwin_on_game FOREIGN KEY (game_id) REFERENCES game (id);

ALTER TABLE game_winners ADD CONSTRAINT fk_gamwin_on_player FOREIGN KEY (player_id) REFERENCES player (id);

ALTER TABLE hand ADD CONSTRAINT FK_HAND_ON_GAME FOREIGN KEY (game_id) REFERENCES game (id);

ALTER TABLE hand ADD CONSTRAINT FK_HAND_ON_PLAYER FOREIGN KEY (player_id) REFERENCES player (id);

ALTER TABLE hand_cards ADD CONSTRAINT fk_hancar_on_card FOREIGN KEY (card_id) REFERENCES card (id);

ALTER TABLE hand_cards ADD CONSTRAINT fk_hancar_on_hand FOREIGN KEY (hand_id) REFERENCES hand (id);