import { Moment } from 'moment';
import { IForumRoom } from 'app/shared/model/forum-room.model';
import { IUser } from 'app/core/user/user.model';

export interface IForumRoomMessage {
    id?: number;
    message?: string;
    messageDatetime?: Moment;
    forumRoom?: IForumRoom;
    user?: IUser;
}

export class ForumRoomMessage implements IForumRoomMessage {
    constructor(
        public id?: number,
        public message?: string,
        public messageDatetime?: Moment,
        public forumRoom?: IForumRoom,
        public user?: IUser
    ) {}
}
