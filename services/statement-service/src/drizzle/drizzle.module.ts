import { Module } from "@nestjs/common";
import { drizzle } from "drizzle-orm/node-postgres";
import Database from "better-sqlite3";
import { DRIZZLE_INSTANCE } from "./constants";
import * as schema from "./schema";
import { ConfigService } from "@nestjs/config";

@Module({
  providers: [
    {
      provide: DRIZZLE_INSTANCE,
      inject: [ConfigService],
      useFactory: async (configService: ConfigService) => {
        const database = new Database(__dirname + "/sqlite.db");
        return drizzle(database, {
          schema,
        });
      },
    },
  ],
  exports: [DRIZZLE_INSTANCE],
})
export class DrizzleModule { }
