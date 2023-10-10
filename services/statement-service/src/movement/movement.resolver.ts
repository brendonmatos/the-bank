import { Args, Resolver, Query, ResolveField, Parent } from "@nestjs/graphql";
import * as GraphqlTypes from "../graphql.types";
import { MovementsService } from "./movement.service";

@Resolver("Movement")
export class MovementResolver {
  constructor(private readonly movementService: MovementsService) { }

  @Query("list")
  async getList(
    @Args("query") query: GraphqlTypes.MovementListQuery,
  ): Promise<Omit<GraphqlTypes.Movement, "account">[]> {
    console.log(query.category);
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

  @ResolveField("receipt")
  async getReceipt(
    @Parent() movement: GraphqlTypes.Movement,
  ): Promise<GraphqlTypes.Receipt> {
    return {
      id: "1",
      url: "https://www.google.com",
    };
  }
}
