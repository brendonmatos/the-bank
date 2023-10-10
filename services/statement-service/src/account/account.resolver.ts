import { Args, Resolver, Query, ResolveField, Parent } from "@nestjs/graphql";
import * as GraphqlTypes from "../graphql.types";
import { AccountService } from "./account.service";

@Resolver("Account")
export class AccountResolver {
  constructor(private readonly accountService: AccountService) { }

  @Query("account")
  async getAccount(
    @Args("id") id: string,
  ): Promise<Omit<GraphqlTypes.Account, "movements">> {
    return {
      id: id,
      name: "Sample Account",
      balance: 1000,
      currency: GraphqlTypes.MovementCurrency.USD,
    };
  }

  @ResolveField("movements")
  async getMovements(
    @Parent() account: GraphqlTypes.Account,
  ): Promise<Omit<GraphqlTypes.Movement, "account">[]> {
    return [
      {
        id: "1",
        amount: 100,
        date: new Date(),
        description: "Sample Movement",
        category: GraphqlTypes.MovementCategory.FOOD,
        currency: GraphqlTypes.MovementCurrency.USD,
        status: GraphqlTypes.MovementStatus.CONFIRMED,
        type: GraphqlTypes.MovementType.EXPENSE,
        transactionId: "Sample Transaction ID",
      },
    ];
  }
}
