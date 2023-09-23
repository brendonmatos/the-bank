import { Field, Int, ObjectType } from '@nestjs/graphql';
import { Account } from './account.model';

@ObjectType()
export class Movement {
  @Field(type => Int)
  id: number;

  @Field({ nullable: true })
  amount?: number;

  @Field()
  timestamp: Date;

  @Field(_ => Account)
  from: Account;

  @Field(_ => Account)
  to: Account;
}