// hàm quét thẻ checkin
package states;
import models.Member;
import models.Membership;

public interface MembershipState {
    void handleCheckIn(Member member, Membership context);
    void renew(Membership context);
    void expire(Membership context);
    String getStatusName();
}