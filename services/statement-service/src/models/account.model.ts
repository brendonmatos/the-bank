import { Field, Int, ObjectType } from '@nestjs/graphql';
import { Movement } from './movement.model';

@ObjectType()
export class Account {
  @Field((_) => Int)
  id: number;

  @Field((_) => [])
  movements: Movement[];
}
