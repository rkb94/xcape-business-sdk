package com.chadev.xcape.admin.service;

import com.chadev.xcape.admin.repository.MerchantRepository;
import com.chadev.xcape.admin.repository.ThemeRepository;
import com.chadev.xcape.admin.util.S3Uploader;
import com.chadev.xcape.core.domain.dto.PriceDto;
import com.chadev.xcape.core.domain.dto.ThemeDto;
import com.chadev.xcape.core.domain.entity.Merchant;
import com.chadev.xcape.core.domain.entity.Price;
import com.chadev.xcape.core.domain.entity.Theme;
import com.chadev.xcape.core.repository.CorePriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThemeService {

    private final MerchantRepository merchantRepository;
    private final ThemeRepository themeRepository;
    private final CorePriceRepository priceRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void createThemeByMerchantId(Long merchantId, ThemeDto themeDto, MultipartHttpServletRequest request, List<PriceDto> priceDtoList) throws IOException {
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(IllegalArgumentException::new);
        imageUpload(themeDto, request);
        Theme newTheme = Theme.builder()
                .merchant(merchant)
                .activity(themeDto.getActivity())
                .bgImagePath(themeDto.getBgImagePath())
                .colorCode(themeDto.getColorCode())
                .description(themeDto.getDescription())
                .difficulty(themeDto.getDifficulty())
                .genre(themeDto.getGenre())
                .hasXKit(themeDto.getHasXKit())
                .isCrimeScene(themeDto.getIsCrimeScene())
                .mainImagePath(themeDto.getMainImagePath())
                .minParticipantCount(themeDto.getMinParticipantCount())
                .maxParticipantCount(themeDto.getMaxParticipantCount())
                .nameKo(themeDto.getNameKo())
                .nameEn(themeDto.getNameEn())
                .observation(themeDto.getObservation())
                .point(themeDto.getPoint())
                .reasoning(themeDto.getReasoning())
                .teamwork(themeDto.getTeamwork())
                .youtubeLink(themeDto.getYoutubeLink())
                .build();
        Theme savedTheme = themeRepository.save(newTheme);
        for (PriceDto priceDto : priceDtoList) {
            priceRepository.save(new Price(priceDto, savedTheme));
        }
    }

    @Transactional
    public void modifyThemeDetail(Long themeId, ThemeDto themeDto, MultipartHttpServletRequest request) throws IOException {
        Theme updateTheme = themeRepository.findById(themeId).orElseThrow(IllegalArgumentException::new);
        imageUpload(themeDto, request);
        updateTheme.setNameKo(themeDto.getNameKo());
        updateTheme.setNameEn(themeDto.getNameEn());
        updateTheme.setMainImagePath(themeDto.getMainImagePath());
        updateTheme.setBgImagePath(themeDto.getBgImagePath());
        updateTheme.setTimetable(themeDto.getTimetable());
        updateTheme.setDescription(themeDto.getDescription());
        updateTheme.setReasoning(themeDto.getReasoning());
        updateTheme.setObservation(themeDto.getObservation());
        updateTheme.setActivity(themeDto.getActivity());
        updateTheme.setTeamwork(themeDto.getTeamwork());
        updateTheme.setMinParticipantCount(themeDto.getMinParticipantCount());
        updateTheme.setMaxParticipantCount(themeDto.getMaxParticipantCount());
        updateTheme.setDifficulty(themeDto.getDifficulty());
        updateTheme.setGenre(themeDto.getGenre());
        updateTheme.setPoint(themeDto.getPoint());
        updateTheme.setYoutubeLink(themeDto.getYoutubeLink());
        updateTheme.setColorCode(themeDto.getColorCode());
        updateTheme.setHasXKit(themeDto.getHasXKit());
        updateTheme.setIsCrimeScene(themeDto.getIsCrimeScene());
    }

    public void imageUpload(ThemeDto themeDto, MultipartHttpServletRequest request) throws IOException {
        MultipartFile mainImage = request.getFile("mainImage");
        MultipartFile bgImage = request.getFile("bgImage");
        if (mainImage != null) {
            themeDto.setMainImagePath(s3Uploader.upload(mainImage, Long.toString(themeDto.getId())));
        }
        if (bgImage != null) {
            themeDto.setBgImagePath(s3Uploader.upload(bgImage, Long.toString(themeDto.getId())));
        }
    }
}
