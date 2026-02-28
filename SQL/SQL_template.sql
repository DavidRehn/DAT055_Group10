-- PK name
CREATE TABLE Users(       -- Automatisk ökande identifier för varje användare, identifieras av användarnamn, görs så att ifall namn ändras så ändras inte också för många andra tabeller
    name TEXT PRIMARY KEY,                   
    login TEXT NOT NULL
);

CREATE TABLE Chats(
    title TEXT PRIMARY KEY-- If group chat, can have title, otherwise if dm, null
);

-- PK sender && message_date (kan ändras vid behov)
CREATE TABLE Messages(
    sender_id INT NOT NULL REFERENCES Users(user_id) ON DELETE CASCADE,
    message_date TIMESTAMPTZ NOT NULL DEFAULT now(),        -- date+time default till när en insert skapas i tabellen
    message_id SERIAL PRIMARY KEY,
    chat_id INT NOT NULL REFERENCES Chats(chat_id) ON DELETE CASCADE,       
    msg_type TEXT NOT NULL, 
    CHECK (msg_type IN ('text', 'image')),
    text_body TEXT,
    image_url TEXT,
    -- Antingen består ett meddelande av text, eller en image, inte både och samt inte inga av dem
    CONSTRAINT msg_type_check CHECK(
        (msg_type = 'text' 
        AND text_body IS NOT NULL
        AND image_url IS NULL)
        OR
        (msg_type = 'image'
        AND image_url IS NOT NULL
        AND text_body IS NULL)
    )
);

CREATE TABLE Chat_Members(
    chat_id INTEGER NOT NULL REFERENCES Chats(chat_id) ON DELETE CASCADE ,  -- Om en chat raderas, ta bort alla rader i denna tabell som har med den chatten att göra
    user_id INTEGER NOT NULL REFERENCES Users(user_id) ON DELETE CASCADE,       -- Samma för en användare
    role TEXT NOT NULL DEFAULT 'member',
    joined_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY(chat_id, user_id)
);

-- ? Messages kan tillhöra chatten direkt via att man lägger till chat_id i messages istället för att ha en ny tabell
-- CREATE TABLE Chat_Messages(
   -- chat_id INTEGER NOT NULL ON DELETE CASCADE,
    -- message_id INTEGER NOT NULL PRIMARY KEY ON DELETE CASCADE,
    -- UNIQUE(chat_id, message_id),
    -- FOREIGN KEY (chat_id) REFERENCES Chats(chat_id),
    -- FOREIGN KEY (message_id) REFERENCES Chats(message_id)
-- )