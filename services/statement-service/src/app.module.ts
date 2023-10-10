import { Module } from "@nestjs/common";
import { HealthModule } from "./health/health.module";
import { GraphQLModule } from "@nestjs/graphql";
import { ApolloDriver, ApolloDriverConfig } from "@nestjs/apollo";
import { join } from "node:path";
import { MovementModule } from "./movement/movement.module";
import { AccountModule } from "./account/account.module";

@Module({
  imports: [
    GraphQLModule.forRoot<ApolloDriverConfig>({
      driver: ApolloDriver,
      typePaths: ["./**/*.gql"],
      definitions: {
        path: join(process.cwd(), "src/graphql.types.ts"),
        outputAs: "interface",
      },
    }),
    HealthModule,
    MovementModule,
    AccountModule,
  ],
})
export class AppModule { }
