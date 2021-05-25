
package com.trantiendat.Database;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trantiendat.Adapter.LocationAdapter;
import com.trantiendat.Adapter.LocationAdapterList;
import com.trantiendat.Model.LocationF;
import com.trantiendat.food_delivery.R;
import com.trantiendat.food_delivery.uri.HomeFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.util.ArrayList;


public class AsynTaskURL extends android.os.AsyncTask<Void, Void, ArrayList<LocationF>> {
    Activity context;
    ArrayList<LocationF> locationFArrayList ;
    LocationF locationF;
    LocationAdapter locationAdapter;
    RecyclerView listView;
    ProgressBar progressBar_load;

    public AsynTaskURL(Activity context) {
        this.context = context;
    }

    public String getHTML(String url) {
        String html = "<div class=\"content-container fd-clearbox ng-scope\" ng-if=\"Items.length>0 &amp;&amp; Type!=6\" style=\"\">\n" +
                "       <div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online\" ng-href=\"https://www.now.vn/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g108/1073199/prof/s640x400/foody-upload-api-foody-mobile-a-xin-210412102418.jpg\" ng-src=\"https://images.foody.vn/res/g108/1073199/prof/s640x400/foody-upload-api-foody-mobile-a-xin-210412102418.jpg\" style=\"background: rgb(0, 0, 0);\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">9.8</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">A Xìn - Chân Gà Rút Xương Ngâm Sả Tắc - Nguyễn Hới - Shop Online</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">27 Nguyễn Hới, P. An Lạc, Quận Bình Tân, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_74919112\" ng-href=\"/thanh-vien/foodee_74919112\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g1759/17588535/avt/c100x100/foodee_74919112-avatar-818-637084900260940189.jpg\" src=\"https://images.foody.vn/usr/g1759/17588535/avt/c100x100/foodee_74919112-avatar-818-637084900260940189.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text not-align\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_74919112\" target=\"_blank\" href=\"/thanh-vien/foodee_74919112\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Hoa Tím</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online/binh-luan-12875022\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online/binh-luan-12875022\">Ngonnn&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">35</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">2</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"1073199\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/com-nieu-me-nau\" ng-href=\"https://www.now.vn/ho-chi-minh/com-nieu-me-nau\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g92/916548/prof/s640x400/foody-upload-api-foody-mobile-21-190514163907.jpg\" ng-src=\"https://images.foody.vn/res/g92/916548/prof/s640x400/foody-upload-api-foody-mobile-21-190514163907.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/com-nieu-me-nau\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">1.8</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/com-nieu-me-nau\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Cơm Niêu Mẹ Nấu</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">46 Đồng Xoài, Quận Tân Bình, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_0vqo4z9x\" ng-href=\"/thanh-vien/foodee_0vqo4z9x\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/default/s100/user-default-female.png\" src=\"https://images.foody.vn/default/s100/user-default-female.png\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_0vqo4z9x\" target=\"_blank\" href=\"/thanh-vien/foodee_0vqo4z9x\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Huyền baby</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/com-nieu-me-nau/binh-luan-6126503\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/com-nieu-me-nau/binh-luan-6126503\">Ôi . đã mất công đặt trên ứng dụng rồi mà vẫn ko được tử tế . đặt cơm lẫn canh . mà ko cho thìa .đũa thì ăn kiểu giày .a bốc tay hả quay trở về thời nguyên thủy hả . làm ăn chỉ biết lấy tiền thôi chứ ...</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">4</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">144</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"916548\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my\" ng-href=\"https://www.now.vn/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g102/1015650/prof/s640x400/foody-upload-api-foody-mobile-hmb-200327174214.jpg\" ng-src=\"https://images.foody.vn/res/g102/1015650/prof/s640x400/foody-upload-api-foody-mobile-hmb-200327174214.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">8.2</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Bò Kho Cô Mai - Since 1984 - Phú Mỹ</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">40/7 Phú Mỹ, P. 22, Quận Bình Thạnh, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/anthuinh\" ng-href=\"/thanh-vien/anthuinh\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g1870/18690242/avt/c100x100/foody-avatar-302-637258499620581518.jpg\" src=\"https://images.foody.vn/usr/g1870/18690242/avt/c100x100/foody-avatar-302-637258499620581518.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/anthuinh\" target=\"_blank\" href=\"/thanh-vien/anthuinh\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Ðan Thu Đinh</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my/binh-luan-8526695\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my/binh-luan-8526695\">Ăn lại lần 2 bò kho ở đây, thấy có giảm giá nên đặt lẹ ăn trưa luôn. Nước được cột trong bịch vầy nha nên không bị đổ tháo. Phần mình mua là hủ tiếu bò kho, đổ ra đầy ắp vậy luôn. Nêm nếm vừa đủ, ăn đủ ...</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">13</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">10</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"1015650\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items -->\n" +
                "\n" +
                "    </div>";
        return html;
    }

   public String getHTML2(String url){
        String html = "<div class=\"content-container fd-clearbox ng-scope\" ng-if=\"Items.length>0 &amp;&amp; Type!=6\" style=\"\">\n" +
                "        <!-- ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/chicc-chicc-ga-ran-han-quoc\" ng-href=\"https://www.now.vn/ho-chi-minh/chicc-chicc-ga-ran-han-quoc\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g106/1050940/prof/s640x400/foody-upload-api-foody-mobile-ga-ran-fast-food-201013164404.jpg\" ng-src=\"https://images.foody.vn/res/g106/1050940/prof/s640x400/foody-upload-api-foody-mobile-ga-ran-fast-food-201013164404.jpg\" style=\"background: rgb(0, 0, 0);\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/chicc-chicc-ga-ran-han-quoc\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">8.4</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/chicc-chicc-ga-ran-han-quoc\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Chícc Chícc - Gà Rán Hàn Quốc</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">12 Xóm Chiếu, P. 13, Quận 4, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_unhbx5vc\" ng-href=\"/thanh-vien/foodee_unhbx5vc\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g2265/22648552/avt/c100x100/foody-avatar-690-637440258881609799.jpg\" src=\"https://images.foody.vn/usr/g2265/22648552/avt/c100x100/foody-avatar-690-637440258881609799.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text not-align\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_unhbx5vc\" target=\"_blank\" href=\"/thanh-vien/foodee_unhbx5vc\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Kim Mai Trần</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/chicc-chicc-ga-ran-han-quoc/binh-luan-12893506\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/chicc-chicc-ga-ran-han-quoc/binh-luan-12893506\">Mì trộn, cơm gà rán BBQ Hàn 35K, sốt ngon&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">107</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">101</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"1050940\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/thien-tam-bun-xao-chay-ha-huy-giap\" ng-href=\"https://www.now.vn/ho-chi-minh/thien-tam-bun-xao-chay-ha-huy-giap\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g95/949751/prof/s640x400/foody-upload-api-foody-mobile-yu-190821180058.jpg\" ng-src=\"https://images.foody.vn/res/g95/949751/prof/s640x400/foody-upload-api-foody-mobile-yu-190821180058.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/thien-tam-bun-xao-chay-ha-huy-giap\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">9.4</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/thien-tam-bun-xao-chay-ha-huy-giap\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Thiện Tâm - Bún Xào Chay - Hà Huy Giáp</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">C10 Chợ Tạm Ngã Tư Ga, Hà Huy Giáp, P. Thạnh Lộc, Quận 12, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_qgcczb3b\" ng-href=\"/thanh-vien/foodee_qgcczb3b\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/default/s100/user-default-male.png\" src=\"https://images.foody.vn/default/s100/user-default-male.png\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_qgcczb3b\" target=\"_blank\" href=\"/thanh-vien/foodee_qgcczb3b\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Namnaruto</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/thien-tam-bun-xao-chay-ha-huy-giap/binh-luan-3180194\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/thien-tam-bun-xao-chay-ha-huy-giap/binh-luan-3180194\">Sáng đi làm đi ngang quán... bán khá đông khác... dù kẹt xe mọi người vẫn chờ xung quang xe bún của quán... tuy bán ngay đầu đường.. nhưng món ăn ở đây chưng bày rất đẹp mắt... lại rất sạch sẽ... \n" +
                "Món ...</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">1</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">6</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"949751\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\" style=\"\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/pho-phu-huong-co-la\" ng-href=\"https://www.now.vn/ho-chi-minh/pho-phu-huong-co-la\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g3/26090/prof/s640x400/foody-mobile-phu-huong-pho-tp-hcm.jpg\" ng-src=\"https://images.foody.vn/res/g3/26090/prof/s640x400/foody-mobile-phu-huong-pho-tp-hcm.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/pho-phu-huong-co-la\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">7.1</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/pho-phu-huong-co-la\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Phở Phú Hương Cò Lả</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">15A Huỳnh Đình Hai, P. 14, Quận Bình Thạnh, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/nga.le9064\" ng-href=\"/thanh-vien/nga.le9064\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g938/9374819/avt/c100x100/nga-le9064-avatar-493-636993214029439209.jpg\" src=\"https://images.foody.vn/usr/g938/9374819/avt/c100x100/nga-le9064-avatar-493-636993214029439209.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/nga.le9064\" target=\"_blank\" href=\"/thanh-vien/nga.le9064\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Tran Tran</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/pho-phu-huong-co-la/binh-luan-2286833\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/pho-phu-huong-co-la/binh-luan-2286833\">Giá thì chát thiệt, nhưng tô phở ăn khá ổn. Nước lèo ngon.\n" +
                "Có máy lạnh nha mấy thím, ghé ăn thử cho biết.</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">11</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">46</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"26090\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/katinat-saigon-kafe-nga-7-ly-thai-to\" ng-href=\"https://www.now.vn/ho-chi-minh/katinat-saigon-kafe-nga-7-ly-thai-to\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g108/1079913/prof/s640x400/foody-upload-api-foody-mobile-fo-7c716d9e-210524141520.jpeg\" ng-src=\"https://images.foody.vn/res/g108/1079913/prof/s640x400/foody-upload-api-foody-mobile-fo-7c716d9e-210524141520.jpeg\" style=\"background: rgb(0, 0, 0);\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/katinat-saigon-kafe-nga-7-ly-thai-to\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">9.2</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/katinat-saigon-kafe-nga-7-ly-thai-to\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Katinat Saigon Kafe - Ngã 7 Lý Thái Tổ</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">788 Điện Biên Phủ, P. 10, Quận 10, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/thewolves255\" ng-href=\"/thanh-vien/thewolves255\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g70/696700/avt/c100x100/thewolves255-avatar-413-637575638421364918.jpg\" src=\"https://images.foody.vn/usr/g70/696700/avt/c100x100/thewolves255-avatar-413-637575638421364918.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text not-align\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/thewolves255\" target=\"_blank\" href=\"/thanh-vien/thewolves255\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Paul Pablo</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/katinat-saigon-kafe-nga-7-ly-thai-to/binh-luan-12891808\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/katinat-saigon-kafe-nga-7-ly-thai-to/binh-luan-12891808\">Quán đẹp , không gian thoải mái nhưng hơi đông&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">1</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">0</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"1079913\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/kem-cuon-thai-lan-196b-le-trong-tan\" ng-href=\"https://www.now.vn/ho-chi-minh/kem-cuon-thai-lan-196b-le-trong-tan\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g22/218647/prof/s640x400/foody-mobile-foody-kem-cuon-thai--223-635935611172925066.jpg\" ng-src=\"https://images.foody.vn/res/g22/218647/prof/s640x400/foody-mobile-foody-kem-cuon-thai--223-635935611172925066.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/kem-cuon-thai-lan-196b-le-trong-tan\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">7.8</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/kem-cuon-thai-lan-196b-le-trong-tan\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Kem Cuộn Thái Lan - 169B Lê Trọng Tấn</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">169B Lê Trọng Tấn, P. Sơn Kỳ, Quận Tân Phú, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/shakyn.nghia\" ng-href=\"/thanh-vien/shakyn.nghia\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g99/989236/avt/c100x100/beauty-upload-api-foody-avatar-1b28bbca-5b76-437e-98e8-efc59980fd8b-191110002245.jpg\" src=\"https://images.foody.vn/usr/g99/989236/avt/c100x100/beauty-upload-api-foody-avatar-1b28bbca-5b76-437e-98e8-efc59980fd8b-191110002245.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/shakyn.nghia\" target=\"_blank\" href=\"/thanh-vien/shakyn.nghia\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Minh Nghĩa Huỳnh Nguyễn</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/kem-cuon-thai-lan-196b-le-trong-tan/binh-luan-3846274\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/kem-cuon-thai-lan-196b-le-trong-tan/binh-luan-3846274\">- Vị trí: Quán ngay mặt tiền đường dễ kiếm\n" +
                "- Không gian: Quán lề đường, 1 cái xe bán, có bày bàn ghế và ít bàn ghế trong 1 cái sân nhà nhỏ à\n" +
                "- Đồ ăn: Quán ngoài bán kem cuộn còn bán khoai tây lắc, trà ...</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">2</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">23</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"218647\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/dung-banh-khoai-mo-ong-ich-khiem\" ng-href=\"https://www.now.vn/ho-chi-minh/dung-banh-khoai-mo-ong-ich-khiem\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g21/202197/prof/s640x400/foody-upload-api-foody-mobile-250-200513145353.jpg\" ng-src=\"https://images.foody.vn/res/g21/202197/prof/s640x400/foody-upload-api-foody-mobile-250-200513145353.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/dung-banh-khoai-mo-ong-ich-khiem\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">8.5</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/dung-banh-khoai-mo-ong-ich-khiem\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Dũng Bánh Khoai Mỡ - Ông Ích Khiêm</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">32/90 Ông Ích Khiêm, Quận 11, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_b7f8cebe\" ng-href=\"/thanh-vien/foodee_b7f8cebe\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/default/s100/user-default-female.png\" src=\"https://images.foody.vn/default/s100/user-default-female.png\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text not-align\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_b7f8cebe\" target=\"_blank\" href=\"/thanh-vien/foodee_b7f8cebe\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">foodee_b7f8cebe</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/dung-banh-khoai-mo-ong-ich-khiem/binh-luan-12891770\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/dung-banh-khoai-mo-ong-ich-khiem/binh-luan-12891770\">Bánh ngon&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">10</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">41</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"202197\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/kem-dua-thai-lan-nguyen-chi-thanh\" ng-href=\"https://www.now.vn/ho-chi-minh/kem-dua-thai-lan-nguyen-chi-thanh\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g28/276151/prof/s640x400/foody-mobile-10-jpg-930-636095350955528252.jpg\" ng-src=\"https://images.foody.vn/res/g28/276151/prof/s640x400/foody-mobile-10-jpg-930-636095350955528252.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/kem-dua-thai-lan-nguyen-chi-thanh\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">7.4</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/kem-dua-thai-lan-nguyen-chi-thanh\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Kem Dừa Thái Lan</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">722 Nguyễn Chí Thanh, P. 4, Quận 11, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/coconew2013\" ng-href=\"/thanh-vien/coconew2013\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g168/1678154/avt/c100x100/beauty-upload-api-foody-avatar-33b05614-03d4-4148-945b-65098cd44bdd-191212130106.jpg\" src=\"https://images.foody.vn/usr/g168/1678154/avt/c100x100/beauty-upload-api-foody-avatar-33b05614-03d4-4148-945b-65098cd44bdd-191212130106.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/coconew2013\" target=\"_blank\" href=\"/thanh-vien/coconew2013\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Quán Quách</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/kem-dua-thai-lan-nguyen-chi-thanh/binh-luan-1073367\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/kem-dua-thai-lan-nguyen-chi-thanh/binh-luan-1073367\">Chỗ này quán lề đường mở vào buổi chiều tối - thực đơn hơi đơn giản có mấy món chiên với kem dừa đặc biệt . Kem dừa cũng dễ thương , có xôi than , đậu phộng , mít ... khi ăn kem béo thơm - có kèm theo ...</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">1</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">31</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"276151\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/biscotti-banh-hat-dinh-duong-khai-tri\" ng-href=\"https://www.now.vn/ho-chi-minh/biscotti-banh-hat-dinh-duong-khai-tri\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g106/1054366/prof/s640x400/file_restaurant_photo_ragg_16049-e35819b5-201109190353.jpeg\" ng-src=\"https://images.foody.vn/res/g106/1054366/prof/s640x400/file_restaurant_photo_ragg_16049-e35819b5-201109190353.jpeg\" style=\"background: rgb(0, 0, 0);\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/biscotti-banh-hat-dinh-duong-khai-tri\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">10.0</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/biscotti-banh-hat-dinh-duong-khai-tri\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Biscotti - Bánh Hạt Dinh Dưỡng - Khai Trí</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">10 Khai Trí, P. 6, Quận Tân Bình, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_fhh9txuh\" ng-href=\"/thanh-vien/foodee_fhh9txuh\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/defaultavatar/s100/default-avatar-20130809-18.jpg\" src=\"https://images.foody.vn/defaultavatar/s100/default-avatar-20130809-18.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text not-align\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_fhh9txuh\" target=\"_blank\" href=\"/thanh-vien/foodee_fhh9txuh\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Việt Đại</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/biscotti-banh-hat-dinh-duong-khai-tri/binh-luan-12880110\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/biscotti-banh-hat-dinh-duong-khai-tri/binh-luan-12880110\">Bánh vị ngon, giòn, giảm 30% mua liền 2 hộp ăn cho đã thèm&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">86</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">5</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"1054366\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/artisan-baked-banh-biscotti\" ng-href=\"https://www.now.vn/ho-chi-minh/artisan-baked-banh-biscotti\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g105/1044820/prof/s640x400/file_restaurant_photo_bdrp_16069-9d91658e-201202165702.jpeg\" ng-src=\"https://images.foody.vn/res/g105/1044820/prof/s640x400/file_restaurant_photo_bdrp_16069-9d91658e-201202165702.jpeg\" style=\"background: rgb(0, 0, 0);\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/artisan-baked-banh-biscotti\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">9.9</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/artisan-baked-banh-biscotti\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Artisan Baked - Bánh Biscotti</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">10 Khai Trí, P. 6, Quận Tân Bình, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_y58fzcaf\" ng-href=\"/thanh-vien/foodee_y58fzcaf\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g1443/14425276/avt/c100x100/foodee_y58fzcaf-avatar-933-636999943970008916.jpg\" src=\"https://images.foody.vn/usr/g1443/14425276/avt/c100x100/foodee_y58fzcaf-avatar-933-636999943970008916.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text not-align\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_y58fzcaf\" target=\"_blank\" href=\"/thanh-vien/foodee_y58fzcaf\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Phuong Mai</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/artisan-baked-banh-biscotti/binh-luan-12882723\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/artisan-baked-banh-biscotti/binh-luan-12882723\">Bánh ngon tuyệt vời, đang giảm 30% nữa chứ&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">97</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">19</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"1044820\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/de-nhat-banh-mi\" ng-href=\"https://www.now.vn/ho-chi-minh/de-nhat-banh-mi\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g77/761697/prof/s640x400/foody-upload-api-foody-mobile-2-jpg-180723144809.jpg\" ng-src=\"https://images.foody.vn/res/g77/761697/prof/s640x400/foody-upload-api-foody-mobile-2-jpg-180723144809.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/de-nhat-banh-mi\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">9.0</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/de-nhat-banh-mi\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Đệ Nhất - Bánh Mì</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">373 Nguyễn Ảnh Thủ, Quận 12, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_29b3d696\" ng-href=\"/thanh-vien/foodee_29b3d696\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/default/s100/user-default-female.png\" src=\"https://images.foody.vn/default/s100/user-default-female.png\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_29b3d696\" target=\"_blank\" href=\"/thanh-vien/foodee_29b3d696\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Foodee_29b3d696 tống</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/de-nhat-banh-mi/binh-luan-3048327\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/de-nhat-banh-mi/binh-luan-3048327\">Bánh mì thơm ngon, đặc biệt bánh hamburger gà, hamburger bò, ngon và giá hợp lý, patê và bơ ở đây thì tuyệt vời.loại thập cẩm chỉ có 22k , đặc biệt cũng chỉ có 30k, loại thông thường thì chỉ 15k, ngon ...</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">1</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">5</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"761697\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/ong-chuon-chuon-tra-da-xay-dai-loan\" ng-href=\"https://www.now.vn/ho-chi-minh/ong-chuon-chuon-tra-da-xay-dai-loan\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g73/724239/prof/s640x400/foody-upload-api-foody-mobile-hmn-jpg-180314163505.jpg\" ng-src=\"https://images.foody.vn/res/g73/724239/prof/s640x400/foody-upload-api-foody-mobile-hmn-jpg-180314163505.jpg\" style=\"background: rgb(0, 0, 0);\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/ong-chuon-chuon-tra-da-xay-dai-loan\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">9.4</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/ong-chuon-chuon-tra-da-xay-dai-loan\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Ông Chuồn Chuồn - Trà Đá Xay Đài Loan</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">47E Nguyễn Thông, P. 9, Quận 3, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/thinkim4372\" ng-href=\"/thanh-vien/thinkim4372\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g886/8857395/avt/c100x100/thinkim4372-avatar-153-637075159916274154.jpg\" src=\"https://images.foody.vn/usr/g886/8857395/avt/c100x100/thinkim4372-avatar-153-637075159916274154.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text not-align\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/thinkim4372\" target=\"_blank\" href=\"/thanh-vien/thinkim4372\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Thiên Kim</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/ong-chuon-chuon-tra-da-xay-dai-loan/binh-luan-2035449\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/ong-chuon-chuon-tra-da-xay-dai-loan/binh-luan-2035449\">Thích đá xây khoai môn thêm trân châu. Mà hôm mình mua hết khoai môn ùi&nbsp; &nbsp; &nbsp; &nbsp; </a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">1</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">11</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"724239\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"/ho-chi-minh/tra-sua-han-quoc\" ng-href=\"/ho-chi-minh/tra-sua-han-quoc\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g93/920868/prof/s640x400/foody-upload-api-foody-mobile-tran-chau-vi-caramel-191008200100.jpg\" ng-src=\"https://images.foody.vn/res/g93/920868/prof/s640x400/foody-upload-api-foody-mobile-tran-chau-vi-caramel-191008200100.jpg\" style=\"background: rgb(0, 0, 0);\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">3.4</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/tra-sua-han-quoc\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Trà Sữa Hàn Quốc</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">196 Nguyễn Thị Nhỏ, P. 15, Quận 5, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_qntxzcil\" ng-href=\"/thanh-vien/foodee_qntxzcil\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g1926/19257181/avt/c100x100/foodee_qntxzcil-avatar-929-637184043326313639.jpg\" src=\"https://images.foody.vn/usr/g1926/19257181/avt/c100x100/foodee_qntxzcil-avatar-929-637184043326313639.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_qntxzcil\" target=\"_blank\" href=\"/thanh-vien/foodee_qntxzcil\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Gia Bảo Tatto St</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/tra-sua-han-quoc/binh-luan-4175619\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/tra-sua-han-quoc/binh-luan-4175619\">Vào mua trà sữa thập cẩm nhân viên không thèm đáp trả lại làm xong quay ra đưa trà sữa rồi lấy tiền. Ít nhiều cũng phải nói cám ơn hoặc nhìn khách, gì mà lạnh lùng quá.</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">1</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">1</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"920868\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>";
        return html;
    }

    private ArrayList<LocationF> getArrayByHTML(String html) {
        locationFArrayList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements data = doc.select("div.content-container.fd-clearbox.ng-scope");
        Log.d("TAG", "getArrayByHTML: " + data);
        for (Element E : data.select("div.content-item.ng-scope")) {
            locationF = new LocationF();

//           Element img = E.select("div.avatar a img").first();
//            Element name = E.select("div.items-content.hide-points div.title.fd-text-ellip a.ng-binding").first();
//            Element add = E.select("div.items-content.hide-points div.desc.fd-text-ellip.ng-binding").first();
            // Log.d("check", "getArrayByHTML: " + img.attr("src"));

            String img = E.select("div.avatar a img").attr("src");
            String name = E.select("div.items-content.hide-points div.title.fd-text-ellip a.ng-binding").text();
            String add = E.select("div.items-content.hide-points div.desc.fd-text-ellip.ng-binding").text();


            locationF.setmNameLocation(name);
            locationF.setmAddLocation(add);
            locationF.setmPhotoLocation(img);

            locationFArrayList.add(locationF);

        }
        return locationFArrayList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        locationFArrayList = new ArrayList<>();
        listView = context.findViewById(R.id.lv_LocationF);
        progressBar_load  = context.findViewById(R.id.progressBar_load);
        progressBar_load.setVisibility(View.VISIBLE);
        progressBar_load.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
    }

    @Override
    protected void onPostExecute(ArrayList<LocationF> locationFS) {
        super.onPostExecute(locationFS);
//        LocationAdapterList locationAdapterList = new LocationAdapterList(context, locationFS);
//        listView.setAdapter(locationAdapterList);
        progressBar_load.setVisibility(View.GONE);
        progressBar_load.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));


        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(context));
        locationAdapter = new LocationAdapter(locationFArrayList, context);
        listView.setAdapter(locationAdapter);
        locationAdapter.notifyDataSetChanged();

    }

    @Override
    protected ArrayList<LocationF> doInBackground(Void... voids) {
        String html = getHTML2("https://www.foody.vn/");
        ArrayList<LocationF> locationFS = new ArrayList<LocationF>();
        locationFS = getArrayByHTML(html);

        return locationFS;
    }
}


/*public class AsynTaskURL extends android.os.AsyncTask<Void, View, ArrayList<LocationF>> {
    Activity context;

    public AsynTaskURL(Activity context) {
        this.context = context;
    }

    ArrayList<LocationF> testArrayList = new ArrayList<>();
    LocationF locationF;
    LocationAdapter locationAdapter;
    RecyclerView rcvListLocation;
    ProgressBar progressBar_load;


    public String getHTML(String url) {
        String html = "<div class=\"content-container fd-clearbox ng-scope\" ng-if=\"Items.length>0 &amp;&amp; Type!=6\" style=\"\">\n" +
                "       <div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online\" ng-href=\"https://www.now.vn/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g108/1073199/prof/s640x400/foody-upload-api-foody-mobile-a-xin-210412102418.jpg\" ng-src=\"https://images.foody.vn/res/g108/1073199/prof/s640x400/foody-upload-api-foody-mobile-a-xin-210412102418.jpg\" style=\"background: rgb(0, 0, 0);\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">9.8</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">A Xìn - Chân Gà Rút Xương Ngâm Sả Tắc - Nguyễn Hới - Shop Online</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">27 Nguyễn Hới, P. An Lạc, Quận Bình Tân, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_74919112\" ng-href=\"/thanh-vien/foodee_74919112\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g1759/17588535/avt/c100x100/foodee_74919112-avatar-818-637084900260940189.jpg\" src=\"https://images.foody.vn/usr/g1759/17588535/avt/c100x100/foodee_74919112-avatar-818-637084900260940189.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text not-align\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_74919112\" target=\"_blank\" href=\"/thanh-vien/foodee_74919112\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Hoa Tím</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online/binh-luan-12875022\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/a-xin-chan-ga-rut-xuong-ngam-sa-tac-nguyen-hoi-shop-online/binh-luan-12875022\">Ngonnn&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">35</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">2</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"1073199\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/com-nieu-me-nau\" ng-href=\"https://www.now.vn/ho-chi-minh/com-nieu-me-nau\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g92/916548/prof/s640x400/foody-upload-api-foody-mobile-21-190514163907.jpg\" ng-src=\"https://images.foody.vn/res/g92/916548/prof/s640x400/foody-upload-api-foody-mobile-21-190514163907.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/com-nieu-me-nau\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">1.8</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/com-nieu-me-nau\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Cơm Niêu Mẹ Nấu</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">46 Đồng Xoài, Quận Tân Bình, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/foodee_0vqo4z9x\" ng-href=\"/thanh-vien/foodee_0vqo4z9x\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/default/s100/user-default-female.png\" src=\"https://images.foody.vn/default/s100/user-default-female.png\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/foodee_0vqo4z9x\" target=\"_blank\" href=\"/thanh-vien/foodee_0vqo4z9x\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Huyền baby</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/com-nieu-me-nau/binh-luan-6126503\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/com-nieu-me-nau/binh-luan-6126503\">Ôi . đã mất công đặt trên ứng dụng rồi mà vẫn ko được tử tế . đặt cơm lẫn canh . mà ko cho thìa .đũa thì ăn kiểu giày .a bốc tay hả quay trở về thời nguyên thủy hả . làm ăn chỉ biết lấy tiền thôi chứ ...</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">4</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">144</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"916548\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items --><div class=\"content-item ng-scope\" ng-repeat=\"item in Items\">\n" +
                "            <div class=\"avatar\">\n" +
                "                <a href=\"https://www.now.vn/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my\" ng-href=\"https://www.now.vn/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my\" target=\"_blank\" ng-init=\"ImpressionGa(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\">\n" +
                "                    <img ng-style=\"{'background':item.MobileBgColor}\" onerror=\"this.onerror=null;this.src='/Style/images/deli-dish-no-image.png';\" src=\"https://images.foody.vn/res/g102/1015650/prof/s640x400/foody-upload-api-foody-mobile-hmb-200327174214.jpg\" ng-src=\"https://images.foody.vn/res/g102/1015650/prof/s640x400/foody-upload-api-foody-mobile-hmb-200327174214.jpg\">\n" +
                "                </a>\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-btn-group ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\">\n" +
                "                    <!-- ngIf: item.IsDelivery --><a class=\"deli-btn ng-scope deli\" ng-if=\"item.IsDelivery\" ng-class=\"{'deli':!item.IsBooking}\" href=\"https://www.now.vn/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" target=\"_blank\">\n" +
                "                        <div class=\"logo\"></div>\n" +
                "                        <!-- ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 --><div class=\"content ng-scope\" ng-if=\"item.MasterCategoryId!=2&amp;&amp;item.MasterCategoryId!=3\">Đặt Giao Hàng</div><!-- end ngIf: item.MasterCategoryId!=2&&item.MasterCategoryId!=3 -->\n" +
                "                        <!-- ngIf: item.MasterCategoryId==2||item.MasterCategoryId==3 -->\n" +
                "                    </a><!-- end ngIf: item.IsDelivery -->\n" +
                "                    <!-- ngIf: item.IsBooking -->\n" +
                "                </div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.IsBooking || item.IsDelivery --><div class=\"tabledeli-overlay ng-scope\" ng-if=\"item.IsBooking || item.IsDelivery\"></div><!-- end ngIf: item.IsBooking || item.IsDelivery -->\n" +
                "                <!-- ngIf: item.HasVideo -->\n" +
                "            </div>\n" +
                "            <div class=\"items-content hide-points\">\n" +
                "                <div class=\"review-points green\" ng-class=\"{'green':item.AvgRating>=6,'grey':item.AvgRating==0 || item.AvgRating==null} \">\n" +
                "                    <span class=\"ng-binding\">8.2</span>\n" +
                "                </div>\n" +
                "                <div class=\"title fd-text-ellip\">\n" +
                "                    <a href=\"/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my\" ng-bind=\"item.Name\" target=\"_blank\" ng-click=\"ClickGA(item,'O_DAU_', $index, DistrictId, '#slDistrictPlace option:selected')\" class=\"ng-binding\">Bò Kho Cô Mai - Since 1984 - Phú Mỹ</a>\n" +
                "                </div>\n" +
                "                <div class=\"desc fd-text-ellip ng-binding\" ng-bind=\"item.Address\">40/7 Phú Mỹ, P. 22, Quận Bình Thạnh, TP. HCM</div>\n" +
                "            </div>\n" +
                "            <!-- ngIf: item.LstReview.length<1 -->\n" +
                "            <!-- ngRepeat: rv in item.LstReview | limitTo:1 --><div class=\"items-review ng-scope\" ng-repeat=\"rv in item.LstReview | limitTo:1\">\n" +
                "                <div class=\"avatar\">\n" +
                "                    <a href=\"/thanh-vien/anthuinh\" ng-href=\"/thanh-vien/anthuinh\" target=\"_blank\">\n" +
                "                        <img onerror=\"this.onerror=null;this.src='/Style/images/no-avatar.png';\" ng-src=\"https://images.foody.vn/usr/g1870/18690242/avt/c100x100/foody-avatar-302-637258499620581518.jpg\" src=\"https://images.foody.vn/usr/g1870/18690242/avt/c100x100/foody-avatar-302-637258499620581518.jpg\">\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "                <div class=\"users-content block-with-text\" ng-class=\"{'not-align':rv.ShortReview}\">\n" +
                "                    <a ng-href=\"/thanh-vien/anthuinh\" target=\"_blank\" href=\"/thanh-vien/anthuinh\">\n" +
                "                        <b ng-bind=\"rv.reviewUserDisplayName\" class=\"ng-binding\">Ðan Thu Đinh</b>\n" +
                "                    </a>\n" +
                "                    <a ng-href=\"/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my/binh-luan-8526695\" ng-bind-html=\"rv.Comment\" target=\"_blank\" class=\"ng-binding\" href=\"/ho-chi-minh/bo-kho-co-mai-since-1984-phu-my/binh-luan-8526695\">Ăn lại lần 2 bò kho ở đây, thấy có giảm giá nên đặt lẹ ăn trưa luôn. Nước được cột trong bịch vầy nha nên không bị đổ tháo. Phần mình mua là hủ tiếu bò kho, đổ ra đầy ắp vậy luôn. Nêm nếm vừa đủ, ăn đủ ...</a>\n" +
                "\n" +
                "                </div>\n" +
                "            </div><!-- end ngRepeat: rv in item.LstReview | limitTo:1 -->\n" +
                "\n" +
                "            <div class=\"items-count\">\n" +
                "                <a ng-click=\"ShowPopupReviews(item.Id)\"><i class=\"fa fa-comment\"></i> <span ng-bind=\"item.TotalReviews|formatNK:1\" class=\"ng-binding\">13</span></a>\n" +
                "                <a ng-click=\"ShowPopupGallery(item.Id)\"><i class=\"fa fa-camera\"></i> <span ng-bind=\"item.TotalPictures|formatNK:1\" class=\"ng-binding\">10</span></a>\n" +
                "                \n" +
                "                <a href=\"javascript:void(0)\" class=\"item-save tool-custom-list-add\" ng-class=\"{'saved':item.IsSaved}\" data-id=\"1015650\">\n" +
                "                    <i class=\"fa fa-bookmark\"></i>\n" +
                "                    <span ng-show=\"!item.IsSaved\">Lưu</span>\n" +
                "                    <span ng-show=\"item.IsSaved\" class=\"ng-hide\">Đã lưu</span>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "        </div><!-- end ngRepeat: item in Items -->\n" +
                "\n" +
                "    </div>";
        return html;
    }

    private ArrayList<LocationF> getArrayByHTML(String html) {
        testArrayList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements data = doc.select("div.content-container.fd-clearbox.ng-scope");
        Log.d("TAG", "getArrayByHTML: " + data);
        for (Element E : data.select("div.content-item.ng-scope")) {
            locationF = new LocationF();

//           Element img = E.select("div.avatar a img").first();
//            Element name = E.select("div.items-content.hide-points div.title.fd-text-ellip a.ng-binding").first();
//            Element add = E.select("div.items-content.hide-points div.desc.fd-text-ellip.ng-binding").first();
            // Log.d("check", "getArrayByHTML: " + img.attr("src"));

            String img = E.select("div.avatar a img").attr("src");
            String name = E.select("div.items-content.hide-points div.title.fd-text-ellip a.ng-binding").text();
            String add = E.select("div.items-content.hide-points div.desc.fd-text-ellip.ng-binding").text();


            locationF.setmNameLocation(name);
            locationF.setmAddLocation(add);
            locationF.setmPhotoLocation(img);

            testArrayList.add(locationF);

        }
        return testArrayList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        rcvListLocation = context.findViewById(R.id.rcvListLocation);
        progressBar_load = context.findViewById(R.id.progressBar_load);
        progressBar_load.setVisibility(View.VISIBLE);
        progressBar_load.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
    }

    @Override
    protected void onPostExecute(ArrayList<LocationF> locationFS) {
        super.onPostExecute(locationFS);
        progressBar_load.setVisibility(View.GONE);
        progressBar_load.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));

        rcvListLocation.setHasFixedSize(true);
        rcvListLocation.setLayoutManager(new LinearLayoutManager(context));
        locationAdapter = new LocationAdapter(testArrayList, context);
        rcvListLocation.setAdapter(locationAdapter);
        locationAdapter.notifyDataSetChanged();

    }

    @Override
    protected ArrayList<LocationF> doInBackground(Void... voids) {
        String html = getHTML("https://www.foody.vn/");
        ArrayList<LocationF> locationFS = new ArrayList<LocationF>();
        locationFS = getArrayByHTML(html);

        return locationFS;
    }
}*/

