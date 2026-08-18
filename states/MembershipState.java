package states;

import models.Member;
import models.Membership;

public interface MembershipState {
    void handleCheckIn(Member member, Membership context);
}