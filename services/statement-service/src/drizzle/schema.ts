import { sqliteTable, text, integer } from "drizzle-orm/sqlite-core";

export const account = sqliteTable("account", {
  id: integer("id").primaryKey(),
  name: text("name"),
  balance: integer("balance"),
  currency: text("currency", {
    enum: ["USD", "EUR", "GBP"],
  }),
});

export const movement = sqliteTable("movement", {
  id: integer("id").primaryKey(),
  amount: integer("amount"),
  date: integer("date", { mode: "timestamp" }),
  description: text("description"),
  currency: text("currency", {
    enum: ["USD", "EUR", "GBP"],
  }),
  status: text("status", {
    enum: ["CONFIRMED", "PENDING"],
  }),
  type: text("type", {
    enum: ["EXPENSE", "INCOME"],
  }),
  transactionId: text("transactionId"),
  accountId: integer("accountId"),
});
